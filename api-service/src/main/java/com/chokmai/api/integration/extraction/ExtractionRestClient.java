package com.chokmai.api.integration.extraction;

import com.chokmai.common.dto.extraction.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import java.io.File;

@RegisterRestClient(configKey = "tensorlake-api")
@Path("/documents/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface ExtractionRestClient {

    // DocumentIngestion endpoints
    @PUT
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    FileUploadResponse uploadFile(
            @HeaderParam("Authorization") String authToken,
            @RestForm("file_bytes") @PartType(MediaType.APPLICATION_OCTET_STREAM) File file,
            @RestForm("labels") String labels // JSON string of labels
    );

    // List all files in TensorLake
    @GET
    @Path("/files")
    FileListResponse listFiles(
            @HeaderParam("Authorization") String authToken,
            @QueryParam("cursor") String cursor,
            @QueryParam("limit") Integer limit
    );


    // Delete file by file_id
    @DELETE
    @Path("/files/{file_id}")
    void deleteFile(
            @HeaderParam("Authorization") String authToken,
            @PathParam("file_id") String fileId
    );

    // Get file metadata
    @GET
    @Path("/files/{file_id}/metadata")
    FileMetadata getFileMetadata(
            @HeaderParam("Authorization") String authToken,
            @PathParam("file_id") String fileId
    );

    // Parsing endpoints
    @POST
    @Path("/read")
    @Consumes(MediaType.APPLICATION_JSON)
    ReadResponse readDocument(
            @HeaderParam("Authorization") String authToken,
            ReadRequest request
    );

    @POST
    @Path("/extract")
    @Consumes(MediaType.APPLICATION_JSON)
    ExtractionResponse createExtractionJob(
            @HeaderParam("Authorization") String authToken,
            ExtractionRequest request
    );
    @POST
    @Path("/classify")
    @Consumes(MediaType.APPLICATION_JSON)
    ClassifyResponse classifyDocument(
            @HeaderParam("Authorization") String authToken,
            ClassifyRequest request
    );

    // Create parse job (more advanced than extract)
    @POST
    @Path("/parse")
    @Consumes(MediaType.APPLICATION_JSON)
    ParseResponse createParseJob(
            @HeaderParam("Authorization") String authToken,
            ParseRequest request
    );
    @GET
    @Path("/parse")
    ParseListResponse listParseJobs(
            @HeaderParam("Authorization") String authorization,
            @QueryParam("cursor") String cursor,
            @QueryParam("limit") Integer limit
    );

    @DELETE
    @Path("/parse/{parse_id}")
    ParseDeleteResponse deleteParseJob(
            @HeaderParam("Authorization") String authorization,
            @PathParam("parse_id") String parseId
    );

    @GET
    @Path("/parse/{parse_id}")
    ParseStatusResponse fetchParseStatus(
            @HeaderParam("Authorization") String authorization,
            @PathParam("parse_id") String parseId
    );
}