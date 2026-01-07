package com.chokmai.persistence.entities.tenants;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "tenants",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_tenant_key", columnNames = "tenant_key")
        },
        indexes = {
                @Index(name = "idx_tenant_key", columnList = "tenant_key")
        }
)
public class TenantEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    /**
     * Human-readable unique tenant key (slug / org identifier)
     * Example: acme, contoso
     */
    @Column(name = "tenant_key", nullable = false, length = 64)
    public String key;

    /**
     * Display name
     */
    @Column(nullable = false, length = 256)
    public String name;

    /**
     * Tenant creation timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    /**
     * Tenant status (ACTIVE, SUSPENDED, DELETED)
     */
    @Column(nullable = false, length = 32)
    public String status;
}
