package com.chokmai.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class TenantContext {

    @Inject
    AuthContext auth;

    public String tenantId() {
        String tenantId = auth.claim("tenant_id");
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Missing tenant_id claim");
        }
        return tenantId;
    }

    public UUID tenantIdAsUuid() {
        return UUID.fromString(tenantId());
    }

    public boolean canAccessTenant(String tenantId) {
        return auth.hasRole("sysadmin") || tenantId.equals(tenantId());
    }
}

