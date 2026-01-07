package com.chokmai.api.processors;

import com.chokmai.common.dto.IdResponse;
import com.chokmai.domain.processors.CreateProcessorRequest;
import com.chokmai.domain.processors.ProcessorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.chokmai.common.dto.processoros.Processor;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@Path("/processors")
@RolesAllowed({})
@Produces(MediaType.APPLICATION_JSON)
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class ProcessorResource {

    @Inject
    ProcessorService processorService;

    @POST
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public IdResponse create(CreateProcessorRequest req) {
        return processorService.create(req);
    }

    @GET
    @RolesAllowed({"viewer", "extractor", "admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public List<Processor> list() {
        return processorService.list();
    }
}

