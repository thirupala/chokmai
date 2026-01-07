package com.chokmai.integration.extraction;

import com.chokmai.api.integration.extraction.ExtractionRestClient;
import com.chokmai.common.dto.extraction.*;
import com.chokmai.common.dto.jobs.JobResponse;
import com.chokmai.common.dto.jobs.UploadUrlResponse;
import com.chokmai.domain.jobs.CreateJobRequest;
import com.chokmai.domain.jobs.JobUploadForm;
import com.chokmai.integration.stripe.CreditService;
import com.chokmai.persistence.entities.jobs.JobEntity;
import com.chokmai.persistence.repositories.jobs.JobRepository;
import com.chokmai.persistence.repositories.llms.LlmModelRepository;
import com.chokmai.security.TenantContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class ExtractionService {

    @Inject
    @RestClient
    ExtractionRestClient extractionClient;

    @Inject
    S3Presigner s3Presigner;

    @ConfigProperty(name = "uploads.bucket",defaultValue = "uploads")
    String bucket;

    @Inject
    JobRepository jobRepository;
    @Inject
    CreditService creditService;

    @Inject
    TenantContext tenantContext;

    @Inject
    LlmModelRepository llmModelRepository;

    @ConfigProperty(name = "tensorlake.api.token")
    String tensorLakeToken;

    public ExtractionService(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    @Inject
    private String auth() {
        return "Bearer " + tensorLakeToken;
    }

    /* =========================
       FILE INGESTION
       ========================= */

    public FileUploadResponse uploadFile(File file, String labelsJson) {
        return extractionClient.uploadFile(auth(), file, labelsJson);
    }

    public FileListResponse listFiles(String cursor, Integer limit) {
        return extractionClient.listFiles(auth(), cursor, limit);
    }

    public void deleteFile(String fileId) {
        extractionClient.deleteFile(auth(), fileId);
    }

    public FileMetadata getFileMetadata(String fileId) {
        return extractionClient.getFileMetadata(auth(), fileId);
    }

    /* =========================
       JOB ORCHESTRATION
       ========================= */
    public ParseResponse startParse(UUID jobId) {
        JobEntity job = jobRepository.findById(jobId);
        if (job == null) {
            throw new NotFoundException("Job not found: " + jobId);
        }
        // Build file reference from known storage key
        String objectKey = String.format(
                "%s/%s/%s/%s/%s",
                job.tenantId,
                job.projectId,
                job.processor.id,
                job.id,
                job.fileName
        );
        // TensorLake ParseRequest
        ParseRequest parseRequest = new ParseRequest();

        // REQUIRED: file_id from TensorLake upload
        parseRequest.file_id = job.extractionFileId;

        // Optional but recommended metadata
        parseRequest.file_name = job.fileName;
        parseRequest.mime_type = "application/pdf";

        // All pages by default
        parseRequest.page_range = null;

        // Parsing options (explicit defaults)
        ParseRequest.ParsingOptions parsingOptions = new ParseRequest.ParsingOptions();
        parsingOptions.table_output_mode = "html";
        parsingOptions.table_parsing_format = "tsr";
        parsingOptions.chunking_strategy = "none";
        parsingOptions.signature_detection = false;
        parsingOptions.remove_strikethrough_lines = false;
        parsingOptions.skew_detection = false;
        parsingOptions.disable_layout_detection = false;
        parsingOptions.ignore_sections = List.of();
        parsingOptions.cross_page_header_detection = false;
        parsingOptions.include_images = false;
        parsingOptions.barcode_detection = false;
        parsingOptions.ocr_model = "model01";

        parseRequest.parsing_options = parsingOptions;

        // No structured extraction by default
        parseRequest.structured_extraction_options = null;

        // No page classification by default
        parseRequest.page_classifications = null;

        // Enrichment options (explicit defaults)
        ParseRequest.EnrichmentOptions enrichmentOptions =
                new ParseRequest.EnrichmentOptions();
        enrichmentOptions.table_summarization = false;
        enrichmentOptions.table_summarization_prompt = null;
        enrichmentOptions.figure_summarization = false;
        enrichmentOptions.figure_summarization_prompt = null;
        enrichmentOptions.include_full_page_image = false;

        parseRequest.enrichment_options = enrichmentOptions;

        // Labels for traceability
        parseRequest.labels = Map.of(
                "tenantId", job.tenantId.toString(),
                "jobId", job.id.toString(),
                "projectId", job.projectId.toString()
        );
        ParseResponse response = extractionClient.createParseJob(auth(), parseRequest);
        // Persist parse_id for tracking
        job.extractionJobId = response.parseId();
        job.status = "processing";
        jobRepository.persist(job);

        return response;
    }
    public void cancelJob(UUID jobId) {
        JobEntity job = jobRepository.findById(jobId);
        if (job == null) {
            return;
        }
        // Cancel TensorLake parse if exists
        if (job.extractionJobId != null) {
            extractionClient.deleteParseJob(auth(), job.extractionJobId);
        }
        // Release reserved credits
        creditService.release(job.tenantId);//TODO
        job.status = "cancelled";
        job.completedAt = Instant.now();
        jobRepository.persist(job);
    }
    /**
     * Async job submission.
     * Flow:
     * 1. Generate jobId
     * 2. Generate presigned upload URL
     * 3. Return jobId + uploadUrl
     *
     * Parsing is triggered later via POST /jobs/{id}/start-parse
     */
    public UploadUrlResponse submitAsync(CreateJobRequest request) {

        // 1. Resolve tenant
        UUID tenantId = UUID.fromString(tenantContext.tenantId());

        // 2. Generate stable job ID
        UUID jobId = UUID.randomUUID();

        // 3. Build storage object key
        // tenant/project/processor/jobId/originalFileName
        String objectKey = String.format(
                "%s/%s/%s/%s/%s",
                tenantId,
                request.projectId(),
                request.processorId(),
                jobId,
                request.fileName()
        );

        // 4. Generate presigned upload URL
        String uploadUrl = generatePresignedUploadUrl(objectKey, request.contentType());

        // 5. Return response
        return new UploadUrlResponse(
                jobId,
                uploadUrl
        );
    }


    public ReadResponse submitSync(JobUploadForm form) {
        // 1.Upload file to TensorLake
        FileUploadResponse uploadResponse =
                extractionClient.uploadFile(
                        auth(),
                        form.file.filePath().toFile(),
                        "{}" // default empty labels for upload
                );

        // 2️Build READ request
        ReadRequest readRequest = new ReadRequest();
        readRequest.file_id = uploadResponse.fileId();
        readRequest.file_name = form.file.fileName();
        readRequest.mime_type = form.file.contentType();

        // All pages by default (TensorLake treats null as "all")
        readRequest.page_range = null;

        // 3.Parsing options (explicit defaults)
        ReadRequest.ParsingOptions parsingOptions = new ReadRequest.ParsingOptions();
        parsingOptions.table_output_mode = "html";
        parsingOptions.table_parsing_format = "tsr";
        parsingOptions.chunking_strategy = "none";
        parsingOptions.signature_detection = false;
        parsingOptions.remove_strikethrough_lines = false;
        parsingOptions.skew_detection = false;
        parsingOptions.disable_layout_detection = false;
        parsingOptions.ignore_sections = List.of();
        parsingOptions.cross_page_header_detection = false;
        parsingOptions.include_images = false;
        parsingOptions.barcode_detection = false;
        parsingOptions.ocr_model = "model01";

        readRequest.parsing_options = parsingOptions;

        // 4.Erichment options (explicit defaults)
        ReadRequest.EnrichmentOptions enrichmentOptions =
                new ReadRequest.EnrichmentOptions();
        enrichmentOptions.table_summarization = false;
        enrichmentOptions.table_summarization_prompt = null;
        enrichmentOptions.figure_summarization = false;
        enrichmentOptions.figure_summarization_prompt = null;
        enrichmentOptions.include_full_page_image = false;

        readRequest.enrichment_options = enrichmentOptions;

        // 5.Labels (safe defaults)
        readRequest.labels = Map.of(
                "projectId", form.projectId,
                "processorId", form.processorId
        );

        // 6.Call TensorLake READ
        return extractionClient.readDocument(auth(), readRequest);
    }


    /** Fetch persisted job info (placeholder) */
    public JobResponse getJob(UUID jobId) {
        JobEntity job = jobRepository.findById(jobId);
        if (job == null) {
            throw new NotFoundException("Job not found: " + jobId);
        }
        return new JobResponse(
                job.id,
                job.status,
                job.projectId,
                job.processor != null ? job.processor.id : null,
                job.createdAt,
                job.completedAt
        );
    }
    /** Real-time status from TensorLake */
    public JobResponse getRealtimeStatus(String parseId) {

        ParseStatusResponse status =
                extractionClient.fetchParseStatus(auth(), parseId);
        return new JobResponse(
                null,
                status.status,
                null,
                null,
                status.createdAt,
                status.finishedAt
        );
    }


    /* =========================
       DIRECT EXTRACTION APIs
       ========================= */

    public ParseResponse createParse(ParseRequest request) {
        return extractionClient.createParseJob(auth(), request);
    }
    public ParseStatusResponse fetchParseStatus(String parseId) {
        return extractionClient.fetchParseStatus(auth(), parseId);
    }

    public ParseListResponse listParses(String cursor, Integer limit) {
        return extractionClient.listParseJobs(auth(), cursor, limit);
    }

    public ParseDeleteResponse deleteParse(String parseId) {
        return extractionClient.deleteParseJob(auth(), parseId);
    }

    public ClassifyResponse classify(ClassifyRequest request) {
        return extractionClient.classifyDocument(auth(), request);
    }

    public ReadResponse read(ReadRequest request) {
        return extractionClient.readDocument(auth(), request);
    }
    private String generatePresignedUploadUrl(String objectKey, String contentType) {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .putObjectRequest(putObjectRequest)
                        .signatureDuration(Duration.ofMinutes(15)) // expiry
                        .build();

        return s3Presigner.presignPutObject(presignRequest)
                .url()
                .toString();
    }

    public ExtractionResponse extract(ExtractionRequest request) {
        return  extractionClient.createExtractionJob(auth(),request);
    }
}
