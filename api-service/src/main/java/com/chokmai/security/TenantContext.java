package com.chokmai.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TenantContext {

    @Inject
    JsonWebToken jwt;

    /**
     * Returns tenant ID as String from JWT claim.
     * Claim name: tenant_id
     */
    public String tenantId() {
        return jwt.getClaim("tenant_id");
    }

    /**
     * Returns the actor performing the request.
     * Preference order:
     * 1. email
     * 2. preferred_username
     * 3. subject (sub)
     */
    public String actor() {
        return Optional.ofNullable(jwt.getClaim("email"))
                .or(() -> Optional.ofNullable(jwt.getClaim("preferred_username")))
                .orElse(jwt.getSubject()).toString();
    }

    /**
     * Returns tenant ID as UUID.
     * Throws IllegalStateException if missing or invalid.
     */
    public UUID tenantIdAsUuid() {
        String tenantId = tenantId();

        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Missing tenant_id claim in JWT");
        }

        try {
            return UUID.fromString(tenantId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException(
                    "Invalid tenant_id format, expected UUID: " + tenantId,
                    ex
            );
        }
    }
}
