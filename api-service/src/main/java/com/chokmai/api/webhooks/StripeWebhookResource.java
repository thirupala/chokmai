package com.chokmai.api.webhooks;

import com.chokmai.domain.billing.StripeEventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/webhooks/stripe")
@Tag(name = "Webhooks")
@APIResponses({
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class StripeWebhookResource {
    @Inject
    StripeEventService stripeEventService;
    @POST
    @Consumes("application/json")
    @Operation(
            summary = "Stripe webhook receiver",
            description = """
        Public endpoint.

        - No JWT required
        - Signature verified via Stripe-Signature header
        """
    )
    @APIResponse(responseCode = "200", description = "Event processed")
    public Response handle(
            String payload,
            @HeaderParam("Stripe-Signature") String signature) {

        stripeEventService.processEvent(payload, signature);
        return Response.ok().build();
    }
}


