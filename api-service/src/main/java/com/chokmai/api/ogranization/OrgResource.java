package com.chokmai.api.ogranization;


import com.chokmai.domain.organization.OrgService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/api/v1/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Organizations", description = "Organization management APIs")
public class OrgResource {

    private static final Logger LOG = Logger.getLogger(OrgResource.class);

    @Inject
    OrgService orgService;

    @POST
    @Operation(summary = "Create a new organization", description = "Creates a new organization with the provided details")
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Organization created successfully",
                    content = @Content(schema = @Schema(implementation = OrgService.OrganizationDTO.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid request or organization name already exists"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response create(@Valid OrgService.CreateOrganizationRequest request) {
        LOG.infof("Creating organization with name: %s", request.name());

        OrgService.OrganizationDTO created = orgService.create(request);

        LOG.infof("Organization created with id: %s", created.id());

        return Response
                .created(URI.create("/api/v1/organizations/" + created.id()))
                .entity(created)
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get organization by ID", description = "Retrieves an organization by its unique identifier")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Organization found",
                    content = @Content(schema = @Schema(implementation = OrgService.OrganizationDTO.class))
            ),
            @APIResponse(responseCode = "404", description = "Organization not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getById(
            @Parameter(description = "Organization ID", required = true)
            @PathParam("id") @NotNull UUID id
    ) {
        LOG.infof("Fetching organization with id: %s", id);

        OrgService.OrganizationDTO organization = orgService.getById(id);

        return Response.ok(organization).build();
    }

    @GET
    @Operation(summary = "Get all organizations", description = "Retrieves a paginated list of all organizations")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Organizations retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrgService.PaginatedResponse.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid pagination parameters"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getAll(
            @Parameter(description = "Page number (0-indexed)")
            @QueryParam("page") @DefaultValue("0") @Min(0) int page,

            @Parameter(description = "Page size")
            @QueryParam("size") @DefaultValue("20") @Min(1) int size
    ) {
        LOG.infof("Fetching organizations - page: %d, size: %d", page, size);

        OrgService.PaginatedResponse<OrgService.OrganizationDTO> response = orgService.getAll(page, size);

        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update organization", description = "Updates an existing organization")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Organization updated successfully",
                    content = @Content(schema = @Schema(implementation = OrgService.OrganizationDTO.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid request or organization name already exists"),
            @APIResponse(responseCode = "404", description = "Organization not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response update(
            @Parameter(description = "Organization ID", required = true)
            @PathParam("id") @NotNull UUID id,

            @Valid OrgService.UpdateOrganizationRequest request
    ) {
        LOG.infof("Updating organization with id: %s", id);

        OrgService.OrganizationDTO updated = orgService.update(id, request);

        LOG.infof("Organization updated: %s", updated.id());

        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete organization", description = "Deletes an organization by ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Organization deleted successfully"),
            @APIResponse(responseCode = "404", description = "Organization not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response delete(
            @Parameter(description = "Organization ID", required = true)
            @PathParam("id") @NotNull UUID id
    ) {
        LOG.infof("Deleting organization with id: %s", id);

        orgService.delete(id);

        LOG.infof("Organization deleted: %s", id);

        return Response.noContent().build();
    }

    @GET
    @Path("/search")
    @Operation(summary = "Search organizations", description = "Search organizations by name pattern")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = OrgService.OrganizationDTO.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid search query"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response search(
            @Parameter(description = "Name search pattern", required = true)
            @QueryParam("q") @NotNull String query
    ) {
        LOG.infof("Searching organizations with query: %s", query);

        List<OrgService.OrganizationDTO> results = orgService.searchByName(query);

        return Response.ok(results).build();
    }

    @GET
    @Path("/recent")
    @Operation(summary = "Get recent organizations", description = "Retrieves the most recently created organizations")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Recent organizations retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrgService.OrganizationDTO.class))
            ),
            @APIResponse(responseCode = "400", description = "Invalid limit parameter"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getRecent(
            @Parameter(description = "Maximum number of results (1-100)")
            @QueryParam("limit") @DefaultValue("10") @Min(1) int limit
    ) {
        LOG.infof("Fetching recent organizations - limit: %d", limit);

        List<OrgService.OrganizationDTO> recent = orgService.getRecent(limit);

        return Response.ok(recent).build();
    }

    @HEAD
    @Path("/{id}")
    @Operation(summary = "Check if organization exists", description = "Checks if an organization exists by ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Organization exists"),
            @APIResponse(responseCode = "404", description = "Organization not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response exists(
            @Parameter(description = "Organization ID", required = true)
            @PathParam("id") @NotNull UUID id
    ) {
        boolean exists = orgService.exists(id);

        return exists
                ? Response.ok().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}
