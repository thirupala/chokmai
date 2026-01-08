package com.chokmai.api.audit;

import com.chokmai.common.dto.audit.AuditLog;
import com.chokmai.domain.audit.AuditQueryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;


@Path("/audit/logs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Audit", description = "Audit and compliance logs")
@SecurityRequirement(name = "bearerAuth")
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class AuditResource {

  @Inject
  AuditQueryService auditQueryService;

  @GET
  @RolesAllowed({"admin", "sysadmin"})
  @Operation(
          summary = "List audit logs",
          description = """
        Returns audit logs.
        - **admin** → logs scoped to their tenant
        - **sysadmin** → logs across all tenants
        """
  )
  @APIResponses({
          @APIResponse(responseCode = "200", description = "Audit logs returned"),
  })
  public List<AuditLog> list(
          @QueryParam("limit") @DefaultValue("50") int limit,
          @QueryParam("offset") @DefaultValue("0") int offset) {

    return auditQueryService.list(limit, offset);
  }
}