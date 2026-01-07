package com.chokmai.persistence.entities.projects;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "projects",
        indexes = {
                @Index(name = "idx_projects_tenant", columnList = "tenant_id"),
                @Index(name = "idx_projects_tenant_name", columnList = "tenant_id,name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_projects_tenant_name", columnNames = {"tenant_id", "name"})
        }
)
public class ProjectEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    /**
     * Tenant isolation key
     */
    @Column(name = "tenant_id", nullable = false, updatable = false)
    public UUID tenantId;

    /**
     * Human-readable project name
     */
    @Column(nullable = false, length = 128)
    public String name;

    /**
     * Optional description
     */
    @Column(length = 1024)
    public String description;

    /**
     * Creation timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    /**
     * Soft delete flag (recommended for audit/compliance)
     */
    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;
}
