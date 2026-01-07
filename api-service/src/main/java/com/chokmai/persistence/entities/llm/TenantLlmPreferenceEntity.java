package com.chokmai.persistence.entities.llm;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "tenant_llm_preferences",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_tenant_llm_pref",
                columnNames = {"tenant_id"}
        )
)
public class TenantLlmPreferenceEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "default_provider_id")
    private UUID defaultProviderId;

    @Column(name = "default_model_id")
    private UUID defaultModelId;

    @Column(name = "fallback_model_id")
    private UUID fallbackModelId;

    @Column(name = "budget_limit_monthly", precision = 10, scale = 2)
    private BigDecimal budgetLimitMonthly;

    @Column(name = "current_month_spend", precision = 10, scale = 2)
    private BigDecimal currentMonthSpend = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getDefaultProviderId() {
        return defaultProviderId;
    }

    public void setDefaultProviderId(UUID defaultProviderId) {
        this.defaultProviderId = defaultProviderId;
    }

    public UUID getDefaultModelId() {
        return defaultModelId;
    }

    public void setDefaultModelId(UUID defaultModelId) {
        this.defaultModelId = defaultModelId;
    }

    public UUID getFallbackModelId() {
        return fallbackModelId;
    }

    public void setFallbackModelId(UUID fallbackModelId) {
        this.fallbackModelId = fallbackModelId;
    }

    public BigDecimal getBudgetLimitMonthly() {
        return budgetLimitMonthly;
    }

    public void setBudgetLimitMonthly(BigDecimal budgetLimitMonthly) {
        this.budgetLimitMonthly = budgetLimitMonthly;
    }

    public BigDecimal getCurrentMonthSpend() {
        return currentMonthSpend;
    }

    public void setCurrentMonthSpend(BigDecimal currentMonthSpend) {
        this.currentMonthSpend = currentMonthSpend;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

