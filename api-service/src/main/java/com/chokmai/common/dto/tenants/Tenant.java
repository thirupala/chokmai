package com.chokmai.common.dto.tenants;

import com.chokmai.persistence.entities.tenants.TenantEntity;

import java.time.Instant;
import java.util.UUID;

public record Tenant(
        UUID id,
        String key,
        String name,
        String status,
        Instant createdAt
) {

    public static Tenant fromEntity(TenantEntity entity) {
        return new Tenant(
                entity.id,
                entity.key,
                entity.name,
                entity.status,
                entity.createdAt
        );
    }
}
