package com.chokmai.api.llm;

import com.chokmai.common.dto.llm.SchemaResponse;
import com.chokmai.domain.llm.CreateSchemaRequest;
import com.chokmai.domain.llm.LlmSchemaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/llm/schemas")
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
public class LlmSchemaResource {

    @Inject
    LlmSchemaService schemaService;

    @POST
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public SchemaResponse create(CreateSchemaRequest req) {
        return schemaService.create(req);
    }
}
