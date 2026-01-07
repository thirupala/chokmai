package com.chokmai.persistence.repositories.llms;

import com.chokmai.persistence.entities.llm.LlmModelEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LlmModelRepository implements PanacheRepositoryBase<LlmModelEntity, UUID> {

    /**
     * Find all active LLM models
     */
    public Collection<LlmModelEntity> findAllActive() {
        return find("isActive = true ORDER BY provider.displayName, displayName").list();
    }

    /**
     * Find models by provider ID
     */
    public Collection<LlmModelEntity> findByProvider(UUID providerId) {
        return find("provider.id = ?1 AND isActive = true ORDER BY displayName", providerId).list();
    }

    /**
     * Find models by provider key
     */
    public Collection<LlmModelEntity> findByProviderKey(String providerKey) {
        return find("provider.providerKey = ?1 AND isActive = true ORDER BY displayName", providerKey).list();
    }

    /**
     * Find model by provider key and model key
     */
    public Optional<LlmModelEntity> findByProviderAndModelKey(String providerKey, String modelKey) {
        return find("provider.providerKey = ?1 AND modelKey = ?2", providerKey, modelKey)
                .firstResultOptional();
    }

    /**
     * Find models that support JSON schema
     */
    public Collection<LlmModelEntity> findJsonSchemaSupported() {
        return find("isActive = true AND provider.supportsJsonSchema = true ORDER BY provider.displayName, displayName")
                .list();
    }

    /**
     * Find models that support vision
     */
    public Collection<LlmModelEntity> findVisionSupported() {
        return find("isActive = true AND supportsVision = true ORDER BY provider.displayName, displayName")
                .list();
    }

    /**
     * Find models that support function calling
     */
    public Collection<LlmModelEntity> findFunctionCallingSupported() {
        return find("isActive = true AND supportsFunctionCalling = true ORDER BY provider.displayName, displayName")
                .list();
    }

    /**
     * Find models by context window size (minimum)
     */
    public Collection<LlmModelEntity> findByMinContextWindow(int minContextWindow) {
        return find("isActive = true AND contextWindow >= ?1 ORDER BY contextWindow DESC", minContextWindow)
                .list();
    }

    /**
     * Find cheapest models (by input token cost)
     */
    public Collection<LlmModelEntity> findCheapestModels(int limit) {
        return find("isActive = true ORDER BY costPer1kInputTokens ASC")
                .page(0, limit)
                .list();
    }

    /**
     * Find most expensive models (by input token cost)
     */
    public Collection<LlmModelEntity> findMostExpensiveModels(int limit) {
        return find("isActive = true ORDER BY costPer1kInputTokens DESC")
                .page(0, limit)
                .list();
    }

    /**
     * Find models within cost range
     */
    public Collection<LlmModelEntity> findByCostRange(
            double minInputCost,
            double maxInputCost,
            double minOutputCost,
            double maxOutputCost
    ) {
        return find(
                "isActive = true AND " +
                        "costPer1kInputTokens BETWEEN :minInput AND :maxInput AND " +
                        "costPer1kOutputTokens BETWEEN :minOutput AND :maxOutput " +
                        "ORDER BY costPer1kInputTokens ASC",
                Parameters.with("minInput", minInputCost)
                        .and("maxInput", maxInputCost)
                        .and("minOutput", minOutputCost)
                        .and("maxOutput", maxOutputCost)
        ).list();
    }

    /**
     * Search models by name or description
     */
    public Collection<LlmModelEntity> search(String query) {
        String searchPattern = "%" + query.toLowerCase() + "%";
        return find(
                "isActive = true AND " +
                        "(LOWER(displayName) LIKE ?1 OR LOWER(modelKey) LIKE ?1 OR LOWER(description) LIKE ?1) " +
                        "ORDER BY displayName",
                searchPattern
        ).list();
    }

    /**
     * Find recommended models for a specific use case
     * This is a helper method that can be customized based on your needs
     */
    public Collection<LlmModelEntity> findRecommendedForExtraction() {
        // Prioritize models with good cost/performance balance
        return find(
                "isActive = true AND " +
                        "provider.supportsJsonSchema = true AND " +
                        "contextWindow >= 32000 " +
                        "ORDER BY costPer1kInputTokens ASC"
        ).page(0, 5).list();
    }

    /**
     * Find default model for a tenant (can be customized)
     */
    public Optional<LlmModelEntity> findDefaultModel() {
        // Returns the most cost-effective model with JSON schema support
        return find(
                "isActive = true AND provider.supportsJsonSchema = true " +
                        "ORDER BY costPer1kInputTokens ASC"
        ).firstResultOptional();
    }

    /**
     * Find models by specific capability
     */
    public Collection<LlmModelEntity> findByCapability(String capabilityKey, Object capabilityValue) {
        return find(
                "isActive = true AND " +
                        "jsonb_extract_path_text(capabilities, ?1) = ?2 " +
                        "ORDER BY displayName",
                capabilityKey, capabilityValue.toString()
        ).list();
    }

    /**
     * Count active models by provider
     */
    public long countByProvider(UUID providerId) {
        return count("provider.id = ?1 AND isActive = true", providerId);
    }

    /**
     * Count all active models
     */
    public long countActive() {
        return count("isActive = true");
    }

    /**
     * Get model statistics
     */
    public ModelStatistics getStatistics() {
        ModelStatistics stats = new ModelStatistics();

        stats.totalModels = count("isActive = true");
        stats.totalProviders = count("SELECT COUNT(DISTINCT provider.id) FROM LlmModelEntity WHERE isActive = true");
        stats.visionModels = count("isActive = true AND supportsVision = true");
        stats.functionCallingModels = count("isActive = true AND supportsFunctionCalling = true");

        // Get average costs
        Object[] avgCosts = find(
                "SELECT AVG(costPer1kInputTokens), AVG(costPer1kOutputTokens) FROM LlmModelEntity WHERE isActive = true"
        ).project(Object[].class).firstResult();

        if (avgCosts != null && avgCosts.length == 2) {
            stats.avgInputCost = avgCosts[0] != null ? ((Number) avgCosts[0]).doubleValue() : 0.0;
            stats.avgOutputCost = avgCosts[1] != null ? ((Number) avgCosts[1]).doubleValue() : 0.0;
        }

        return stats;
    }

    /**
     * Activate or deactivate a model
     */
    public void setActive(UUID modelId, boolean active) {
        update("isActive = ?1, updatedAt = CURRENT_TIMESTAMP WHERE id = ?2", active, modelId);
    }

    /**
     * Batch update model costs
     */
    public void updateCosts(UUID modelId, double inputCost, double outputCost) {
        update(
                "costPer1kInputTokens = ?1, costPer1kOutputTokens = ?2, updatedAt = CURRENT_TIMESTAMP WHERE id = ?3",
                inputCost, outputCost, modelId
        );
    }

    // Inner class for statistics
    public static class ModelStatistics {
        public long totalModels;
        public long totalProviders;
        public long visionModels;
        public long functionCallingModels;
        public double avgInputCost;
        public double avgOutputCost;
    }
}