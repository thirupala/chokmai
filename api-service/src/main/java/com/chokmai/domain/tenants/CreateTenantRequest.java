package com.chokmai.domain.tenants;

public record CreateTenantRequest(
        String key,
        String name
) {}
