package com.chokmai.persistence.entities.llm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "llm_providers")
public class LlmProviderEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    @Column(name = "provider_key", unique = true, nullable = false)
    public String providerKey;

    @Column(name = "display_name", nullable = false)
    public String displayName;

    public String description;

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "supports_json_schema")
    public Boolean supportsJsonSchema = true;

    @Column(name = "supports_streaming")
    public Boolean supportsStreaming = false;

    @Column(name = "base_url")
    public String baseUrl;

    @Column(name = "api_version")
    public String apiVersion;

    @Column(name = "created_at")
    public Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    public Instant updatedAt = Instant.now();
}


