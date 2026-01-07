package com.chokmai.persistence.repositories.llms;


import com.chokmai.persistence.entities.llm.LlmUsageLogEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LlmUsageLogRepository implements PanacheRepositoryBase<LlmUsageLogEntity, UUID> {

    /**
     * Find usage logs for a tenant within date range
     */
    public Collection<LlmUsageLogEntity> findByTenantAndDateRange(
            UUID tenantId,
            Instant startDate,
            Instant endDate
    ) {
        return find(
                "#LlmUsageLog.findByTenantAndDateRange",
                Parameters.with("tenantId", tenantId)
                        .and("startDate", startDate)
                        .and("endDate", endDate)
        ).list();
    }

    /**
     * Get total cost for a tenant since a specific date
     */
    public BigDecimal getTotalCostByTenant(UUID tenantId, Instant startDate) {
        BigDecimal result = find(
                "#LlmUsageLog.getTotalCostByTenant",
                Parameters.with("tenantId", tenantId)
                        .and("startDate", startDate)
        ).project(BigDecimal.class).firstResult();

        return result != null ? result : BigDecimal.ZERO;
    }

    /**
     * Find usage logs by job
     */
    public Collection<LlmUsageLogEntity> findByJob(UUID jobId) {
        return find("job.id = ?1 ORDER BY createdAt DESC", jobId).list();
    }

    /**
     * Find usage logs by provider
     */
    public Collection<LlmUsageLogEntity> findByProvider(UUID providerId, Instant startDate, Instant endDate) {
        return find(
                "provider.id = ?1 AND createdAt BETWEEN ?2 AND ?3 ORDER BY createdAt DESC",
                providerId, startDate, endDate
        ).list();
    }

    /**
     * Find usage logs by model
     */
    public Collection<LlmUsageLogEntity> findByModel(UUID modelId, Instant startDate, Instant endDate) {
        return find(
                "model.id = ?1 AND createdAt BETWEEN ?2 AND ?3 ORDER BY createdAt DESC",
                modelId, startDate, endDate
        ).list();
    }

    /**
     * Get usage statistics by tenant
     */
    public UsageStatistics getStatisticsByTenant(UUID tenantId, Instant startDate, Instant endDate) {
        List<Object[]> results = find(
                "SELECT " +
                        "COUNT(*), " +
                        "SUM(inputTokens), " +
                        "SUM(outputTokens), " +
                        "SUM(totalCost), " +
                        "AVG(requestTimeMs) " +
                        "FROM LlmUsageLogEntity " +
                        "WHERE tenantId = ?1 AND createdAt BETWEEN ?2 AND ?3",
                tenantId, startDate, endDate
        ).project(Object[].class).list();

        UsageStatistics stats = new UsageStatistics();
        if (!results.isEmpty() && results.get(0) != null) {
            Object[] row = results.get(0);
            stats.totalRequests = row[0] != null ? ((Number) row[0]).longValue() : 0L;
            stats.totalInputTokens = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            stats.totalOutputTokens = row[2] != null ? ((Number) row[2]).longValue() : 0L;
            stats.totalCost = row[3] != null ? (BigDecimal) row[3] : BigDecimal.ZERO;
            stats.avgRequestTimeMs = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;
        }
        return stats;
    }

    /**
     * Get usage breakdown by provider
     */
    public List<ProviderUsageBreakdown> getProviderBreakdown(UUID tenantId, Instant startDate, Instant endDate) {
        return find(
                "SELECT " +
                        "provider.providerKey, " +
                        "provider.displayName, " +
                        "COUNT(*), " +
                        "SUM(totalCost) " +
                        "FROM LlmUsageLogEntity " +
                        "WHERE tenantId = ?1 AND createdAt BETWEEN ?2 AND ?3 " +
                        "GROUP BY provider.providerKey, provider.displayName " +
                        "ORDER BY SUM(totalCost) DESC",
                tenantId, startDate, endDate
        ).project(Object[].class)
                .list()
                .stream()
                .map(row -> {
                    ProviderUsageBreakdown breakdown = new ProviderUsageBreakdown();
                    breakdown.providerKey = (String) row[0];
                    breakdown.providerName = (String) row[1];
                    breakdown.requestCount = ((Number) row[2]).longValue();
                    breakdown.totalCost = (BigDecimal) row[3];
                    return breakdown;
                })
                .toList();
    }

    /**
     * Get usage breakdown by model
     */
    public List<ModelUsageBreakdown> getModelBreakdown(UUID tenantId, Instant startDate, Instant endDate) {
        return find(
                "SELECT " +
                        "model.modelKey, " +
                        "model.displayName, " +
                        "provider.displayName, " +
                        "COUNT(*), " +
                        "SUM(inputTokens), " +
                        "SUM(outputTokens), " +
                        "SUM(totalCost) " +
                        "FROM LlmUsageLogEntity " +
                        "WHERE tenantId = ?1 AND createdAt BETWEEN ?2 AND ?3 " +
                        "GROUP BY model.modelKey, model.displayName, provider.displayName " +
                        "ORDER BY SUM(totalCost) DESC",
                tenantId, startDate, endDate
        ).project(Object[].class)
                .list()
                .stream()
                .map(row -> {
                    ModelUsageBreakdown breakdown = new ModelUsageBreakdown();
                    breakdown.modelKey = (String) row[0];
                    breakdown.modelName = (String) row[1];
                    breakdown.providerName = (String) row[2];
                    breakdown.requestCount = ((Number) row[3]).longValue();
                    breakdown.inputTokens = ((Number) row[4]).longValue();
                    breakdown.outputTokens = ((Number) row[5]).longValue();
                    breakdown.totalCost = (BigDecimal) row[6];
                    return breakdown;
                })
                .toList();
    }

    /**
     * Get failed requests
     */
    public Collection<LlmUsageLogEntity> findFailedRequests(UUID tenantId, Instant startDate, Instant endDate) {
        return find(
                "tenantId = ?1 AND status = 'failed' AND createdAt BETWEEN ?2 AND ?3 ORDER BY createdAt DESC",
                tenantId, startDate, endDate
        ).list();
    }

    /**
     * Get slow requests (above threshold)
     */
    public Collection<LlmUsageLogEntity> findSlowRequests(
            UUID tenantId,
            int thresholdMs,
            Instant startDate,
            Instant endDate
    ) {
        return find(
                "tenantId = ?1 AND requestTimeMs > ?2 AND createdAt BETWEEN ?3 AND ?4 ORDER BY requestTimeMs DESC",
                tenantId, thresholdMs, startDate, endDate
        ).list();
    }

    /**
     * Get daily usage aggregates
     */
    public List<DailyUsage> getDailyUsage(UUID tenantId, Instant startDate, Instant endDate) {
        return find(
                "SELECT " +
                        "CAST(createdAt AS date), " +
                        "COUNT(*), " +
                        "SUM(inputTokens), " +
                        "SUM(outputTokens), " +
                        "SUM(totalCost) " +
                        "FROM LlmUsageLogEntity " +
                        "WHERE tenantId = ?1 AND createdAt BETWEEN ?2 AND ?3 " +
                        "GROUP BY CAST(createdAt AS date) " +
                        "ORDER BY CAST(createdAt AS date) ASC",
                tenantId, startDate, endDate
        ).project(Object[].class)
                .list()
                .stream()
                .map(row -> {
                    DailyUsage usage = new DailyUsage();
                    usage.date = row[0].toString();
                    usage.requestCount = ((Number) row[1]).longValue();
                    usage.inputTokens = ((Number) row[2]).longValue();
                    usage.outputTokens = ((Number) row[3]).longValue();
                    usage.totalCost = (BigDecimal) row[4];
                    return usage;
                })
                .toList();
    }

    // Inner classes for statistics
    public static class UsageStatistics {
        public long totalRequests;
        public long totalInputTokens;
        public long totalOutputTokens;
        public BigDecimal totalCost;
        public double avgRequestTimeMs;
    }

    public static class ProviderUsageBreakdown {
        public String providerKey;
        public String providerName;
        public long requestCount;
        public BigDecimal totalCost;
    }

    public static class ModelUsageBreakdown {
        public String modelKey;
        public String modelName;
        public String providerName;
        public long requestCount;
        public long inputTokens;
        public long outputTokens;
        public BigDecimal totalCost;
    }

    public static class DailyUsage {
        public String date;
        public long requestCount;
        public long inputTokens;
        public long outputTokens;
        public BigDecimal totalCost;
    }
}
