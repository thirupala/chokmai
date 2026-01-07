package com.chokmai.api.projects;

import com.chokmai.domain.projects.CreateProjectRequest;
import com.chokmai.domain.projects.ProjectService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("/tenants/{tenantId}/projects")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Projects")
@SecurityRequirement(name = "bearerAuth")
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @POST
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    @Operation(summary = "Create project", description = """
            Creates a project under a tenant.
             - **admin** → tenantId must match JWT tenant
             - **sysadmin** → any tenantId allowed
            """
    )
    public UUID create(
            @PathParam("tenantId") String tenantId,
            CreateProjectRequest req) {

        return projectService.create(tenantId, req);
    }
}
