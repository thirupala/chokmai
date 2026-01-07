package com.chokmai.integration.stripe;
import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class StripeConfig {

    @ConfigProperty(name = "stripe.secret-key" )
    String secretKey;

    @PostConstruct
    void init() {
        Stripe.apiKey = secretKey;
    }
}
