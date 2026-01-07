package com.chokmai.api.integration.extraction;

import com.chokmai.common.dto.extraction.*;
import com.chokmai.integration.extraction.ExtractionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.File;

@Path("/api/extraction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class ExtractionResource {

    @Inject
    ExtractionService extractionService;

    /* =========================
       FILE INGESTION
       ========================= */

    @POST
    @Path("/files/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public FileUploadResponse uploadFile(@HeaderParam("Authorization") String authToken,
            @FormParam("file") File file,
            @FormParam("labels") String labels
    ) {
        return extractionService.uploadFile(file, labels);
    }

    @GET
    @Path("/files")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public FileListResponse listFiles(
            @QueryParam("cursor") String cursor,
            @QueryParam("limit") Integer limit
    ) {
        return extractionService.listFiles(cursor, limit);
    }

    @DELETE
    @Path("/files/{fileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public void deleteFile(@PathParam("fileId") String fileId) {
        extractionService.deleteFile(fileId);
    }

    @GET
    @Path("/files/{fileId}/metadata")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public FileMetadata getFileMetadata(@PathParam("fileId") String fileId) {
        return extractionService.getFileMetadata(fileId);
    }

    /* =========================
       PARSING / EXTRACTION
       ========================= */

    @POST
    @Path("/read")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ReadResponse read(ReadRequest request) {
        return extractionService.read(request);
    }

    @POST
    @Path("/extract")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ExtractionResponse extract(ExtractionRequest request) {
        return extractionService.extract(request);
    }

    @POST
    @Path("/classify")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ClassifyResponse classify(ClassifyRequest request) {
        return extractionService.classify(request);
    }

    @POST
    @Path("/parse")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ParseResponse createParse(ParseRequest request) {
        return extractionService.createParse(request);
    }

    @GET
    @Path("/parse")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ParseListResponse listParses(
            @QueryParam("cursor") String cursor,
            @QueryParam("limit") Integer limit
    ) {
        return extractionService.listParses(cursor, limit);
    }

    @GET
    @Path("/parse/{parseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ParseStatusResponse fetchParseStatus(
            @PathParam("parseId") String parseId
    ) {
        return extractionService.fetchParseStatus(parseId);
    }

    @DELETE
    @Path("/parse/{parseId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ParseDeleteResponse deleteParse(
            @PathParam("parseId") String parseId
    ) {
        return extractionService.deleteParse(parseId);
    }
}
