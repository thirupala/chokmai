package com.chokmai.persistence.entities.jobs;

import com.chokmai.persistence.entities.processors.ProcessorEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "jobs",
        indexes = {
                @Index(name = "idx_jobs_tenant", columnList = "tenant_id"),
                @Index(name = "idx_jobs_processor", columnList = "processor_id"),
                @Index(name = "idx_jobs_status", columnList = "status"),
                @Index(name = "idx_jobs_extraction_job_id", columnList = "extraction_job_id"),
                @Index(name = "idx_jobs_external_job_id", columnList = "external_job_id"),
                @Index(name = "idx_jobs_tenant_status", columnList = "tenant_id,status")
        }
)
public class JobEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    /**
     * Tenant isolation key
     */
    @Column(name = "tenant_id", nullable = false, updatable = false)
    public UUID tenantId;

    /**
     * Project this job belongs to
     */
    @Column(name = "project_id", nullable = false, updatable = false)
    public UUID projectId;

    /**
     * Processor ID (denormalized for queries)
     */
    @Column(name = "processor_id", nullable = false, updatable = false, insertable = false)
    public UUID processorId;

    /**
     * External job tracking ID (for background job queues)
     */
    @Column(name = "external_job_id")
    public String externalJobId;

    /**
     * Reference to processor entity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processor_id", nullable = false)
    public ProcessorEntity processor;

    /**
     * TensorLake file ID
     */
    @Column(name = "extraction_file_id", length = 255)
    public String extractionFileId;

    /**
     * TensorLake job/parse ID
     */
    @Column(name = "extraction_job_id", length = 255)
    public String extractionJobId;

    /**
     * Original file name
     */
    @Column(name = "file_name", nullable = false, length = 512)
    public String fileName;

    /**
     * File size in bytes
     */
    @Column(name = "file_size")
    public Long fileSize;

    /**
     * Job status: pending, processing, completed, failed
     */
    @Column(name = "status", nullable = false, length = 32)
    public String status;

    /**
     * Extraction results as JSONB
     */
    @Column(name = "extraction_results", columnDefinition = "jsonb")
    public String extractionResults;

    /**
     * Error message if job failed
     */
    @Column(name = "error_message", length = 2048)
    public String errorMessage;

    /**
     * Number of retry attempts
     */
    @Column(name = "retry_count", nullable = false)
    public Integer retryCount = 0;

    /**
     * Estimated credits before job execution
     */
    @Column(name = "estimated_credits", nullable = false)
    public int estimatedCredits = 0;

    /**
     * Actual credits consumed after job execution
     */
    @Column(name = "actual_credits", nullable = false)
    public int actualCredits = 0;

    /**
     * Creation timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    /**
     * Last update timestamp
     */
    @Column(name = "updated_at")
    public Instant updatedAt;

    /**
     * Completion timestamp
     */
    @Column(name = "completed_at")
    public Instant completedAt;

    /**
     * Soft delete flag
     */
    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    @Column(name = "llm_model_id")
    public UUID llmModelId;
    @Column(name = "llm_input_tokens")
    public Integer llmInputTokens;

    @Column(name = "llm_output_tokens")
    public Integer llmOutputTokens;

    @Column(name = "llm_cost", precision = 10, scale = 6)
    public BigDecimal llmCost;


}