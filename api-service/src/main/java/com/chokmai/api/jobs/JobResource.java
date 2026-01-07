package com.chokmai.api.jobs;

import com.chokmai.common.dto.extraction.ParseResponse;
import com.chokmai.common.dto.extraction.ReadResponse;
import com.chokmai.common.dto.jobs.JobResponse;
import com.chokmai.common.dto.jobs.UploadUrlResponse;
import com.chokmai.domain.jobs.CreateJobRequest;
import com.chokmai.domain.jobs.JobUploadForm;
import com.chokmai.integration.extraction.ExtractionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.UUID;

@Path("/jobs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class JobResource {

    @Inject
    ExtractionService extractionService;

    @POST
    @Path("/{jobId}/start-parse")
    @RolesAllowed({"extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ParseResponse startParse(
            @PathParam("jobId") UUID jobId
    ) {
        return extractionService.startParse(jobId);
    }
    @DELETE
    @Path("/{jobId}")
    @RolesAllowed({"extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public void cancelJob(@PathParam("jobId") UUID jobId) {
        extractionService.cancelJob(jobId);
    }


    /** Async job submission */
    @POST
    @Path("/async")
    @RolesAllowed({"extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public UploadUrlResponse submitAsync(CreateJobRequest request) {
        return extractionService.submitAsync(request);
    }

    /** Sync job submission */
    @POST
    @Path("/sync")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({"extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public ReadResponse submitSync(@BeanParam JobUploadForm form) {
        return extractionService.submitSync(form);
    }

    /** Job details (provider-agnostic placeholder) */
    @GET
    @Path("/{jobId}")
    @RolesAllowed({"viewer", "extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public JobResponse getJob(@PathParam("jobId") UUID jobId) {
        return extractionService.getJob(jobId);
    }

    /** Real-time job status (direct provider lookup) */
    @GET
    @Path("/{jobId}/status/realtime")
    @RolesAllowed({"viewer", "extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public JobResponse getRealtimeStatus(@PathParam("jobId") String jobId) {
        return extractionService.getRealtimeStatus(jobId);
    }
}
