package com.chokmai.api.billing;


import com.chokmai.common.dto.CheckoutSessionResponse;
import com.chokmai.common.dto.CreateCheckoutRequest;
import com.chokmai.domain.billing.BillingService;
import com.chokmai.common.dto.CreditBalance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/billing")
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
public class BillingResource {

    @Inject
    BillingService billingService;

    @Inject
    JsonWebToken jwt;

    /**
     * Tenant admin views current credit balance
     */
    @GET
    @Path("/credits")
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public CreditBalance getCredits() {
        String tenantId = jwt.getClaim("tenant_id");
        return billingService.getCreditBalance(tenantId);
    }

    /**
     * Create Stripe hosted checkout session
     */
    @POST
    @Path("/checkout")
    @RolesAllowed({"admin", "sysadmin"})
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public Response createCheckoutSession(CreateCheckoutRequest request) {
        String tenantId = jwt.getClaim("tenant_id");
        CheckoutSessionResponse response =
                billingService.createCheckoutSession(tenantId, request);
        return Response.ok(response).build();
    }
}
