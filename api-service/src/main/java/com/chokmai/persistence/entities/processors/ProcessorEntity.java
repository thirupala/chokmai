package com.chokmai.persistence.entities.processors;

import com.chokmai.common.enums.ProcessorStatus;
import com.chokmai.persistence.entities.projects.ProjectEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "processors",
        indexes = {
                @Index(name = "idx_processors_tenant", columnList = "tenant_id"),
                @Index(name = "idx_processors_type", columnList = "type")
        }
)
public class ProcessorEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;
    /**
     * Nullable → global processor
     * Non-null → tenant-specific processor
     */
    @Column(name = "tenant_id")
    public UUID tenantId;

    /**
     * Processor name (unique per tenant or global)
     */
    @Column(nullable = false, length = 128)
    public String name;

    /**
     * Processor type
     * Examples: OCR, LLM, RULE_ENGINE
     */
    @Column(nullable = false, length = 64)
    public String type;

    /**
     * Processor version
     */
    @Column(length = 64)
    public String version;

    /**
     * Processor configuration reference (JSON, URI, or key)
     */
    @Column(name = "json_schema", length = 512)
    public String jsonSchema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProcessorStatus status = ProcessorStatus.CREATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    /**
     * Creation timestamp
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    /**
     * Soft delete flag
     */
    @Column(nullable = false)
    public boolean deleted = false;

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public String getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
