package com.chokmai.persistence.entities.llm;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "llm_models")
public class LlmModelEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    public LlmProviderEntity provider;

    @Column(name = "model_key", nullable = false)
    public String modelKey;

    @Column(name = "display_name", nullable = false)
    public String displayName;

    public String description;

    @Column(name = "context_window")
    public Integer contextWindow;

    @Column(name = "max_output_tokens")
    public Integer maxOutputTokens;

    @Column(name = "supports_vision")
    public Boolean supportsVision = false;

    @Column(name = "supports_function_calling")
    public Boolean supportsFunctionCalling = false;

    @Column(name = "cost_per_1k_input_tokens", precision = 10, scale = 6)
    public BigDecimal costPer1kInputTokens;

    @Column(name = "cost_per_1k_output_tokens", precision = 10, scale = 6)
    public BigDecimal costPer1kOutputTokens;

    @Column(name = "is_active")
    public Boolean isActive = true;

    // CORRECT: Use Hibernate 6 native JSON support
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    public Map<String, Object> capabilities;

    @Column(name = "created_at")
    public Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    public Instant updatedAt = Instant.now();
}
