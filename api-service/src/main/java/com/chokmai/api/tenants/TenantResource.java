package com.chokmai.api.tenants;

import com.chokmai.common.dto.IdResponse;
import com.chokmai.common.dto.tenants.Tenant;
import com.chokmai.domain.tenants.CreateTenantRequest;
import com.chokmai.domain.tenants.TenantService;
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

@Path("/tenants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tenants", description = "Tenant lifecycle management")
@APIResponses({
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class TenantResource {

    @Inject
    TenantService tenantService;

    /**
     * Create a new tenant.
     *
     * <p>
     * Role behavior:
     * <ul>
     *   <li><b>admin</b> – creates a tenant within their own organization</li>
     *   <li><b>sysadmin</b> – may create tenants across organizations</li>
     * </ul>
     * </p>
     */
    @POST
    @RolesAllowed({"admin", "sysadmin"})
    @Operation(
            summary = "Create tenant",
            description = """
            Creates a new tenant.

            - **admin** → tenant scoped to the admin's organization
            - **sysadmin** → may create tenants globally
            """
    )
    @APIResponse(
            responseCode = "200",
            description = "Tenant created successfully"
    )
    public IdResponse create(CreateTenantRequest req) {

        return tenantService.create(req);
    }

    /**
     * List tenants.
     *
     * <p>
     * Role behavior:
     * <ul>
     *   <li><b>admin</b> – returns tenants visible to their organization</li>
     *   <li><b>sysadmin</b> – returns all tenants</li>
     * </ul>
     * </p>
     */


    @Operation(
            summary = "List tenants",
            description = """
            Returns a list of tenants.

            - **admin** → tenants scoped to their organization
            - **sysadmin** → all tenants
            """
    )
    @APIResponse(
            responseCode = "200",
            description = "List of tenants"
    )
    @GET
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public List<Tenant> list() {
        return tenantService.listTenants();
    }

    /**
     * Delete a tenant.
     *
     * <p>
     * This operation is destructive and cannot be undone.
     * </p>
     *
     * <p>
     * Role behavior:
     * <ul>
     *   <li><b>admin</b> – may delete tenants they manage</li>
     *   <li><b>sysadmin</b> – may delete any tenant</li>
     * </ul>
     * </p>
     */

    @Operation(
            summary = "Delete tenant",
            description = """
            Deletes a tenant by ID.

            - **admin** → only allowed for managed tenants
            - **sysadmin** → allowed for any tenant
            """
    )

    @DELETE
    @Path("/{tenantId}")
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponse(
            responseCode = "204",
            description = "Tenant deleted successfully"
    )
    public void delete(@PathParam("tenantId") String tenantId) {
        tenantService.delete(tenantId);
    }
}
