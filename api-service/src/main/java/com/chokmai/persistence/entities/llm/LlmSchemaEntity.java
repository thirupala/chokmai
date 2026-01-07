package com.chokmai.persistence.entities.llm;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(
        name = "llm_schemas",
        indexes = {
                @Index(name = "idx_llm_schemas_tenant", columnList = "tenant_id"),
                @Index(name = "idx_llm_schemas_tenant_name", columnList = "tenant_id,name")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_llm_schema_tenant_name",
                        columnNames = {"tenant_id", "name"}
                )
        }
)
public class LlmSchemaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    /**
     * Tenant isolation key
     */
    @Column(name = "tenant_id", nullable = false, updatable = false)
    public UUID tenantId;

    /**
     * Logical schema name (unique per tenant)
     */
    @Column(nullable = false, length = 128)
    public String name;

    /**
     * Optional description
     */
    @Column(length = 1024)
    public String description;

    /**
     * LLM provider (openai, anthropic, azure-openai, etc.)
     */
    @Column(name = "model_provider", nullable = false, length = 64)
    public String modelProvider;

    /**
     * Concrete model name (gpt-4o, claude-3, etc.)
     */
    @Column(name = "model_name", nullable = false, length = 128)
    public String modelName;

    /**
     * JSON schema definition (stored as JSONB)
     */
    @Column(name = "schema_json", nullable = false, columnDefinition = "jsonb")
    @Convert(converter = JsonMapConverter.class)
    public Map<String, Object> schemaJson;

    /**
     * Creation timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    public Instant createdAt;

    /**
     * Soft delete flag (future-proofing)
     */
    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;
}
