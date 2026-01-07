package com.chokmai.persistence.repositories.llms;


import com.chokmai.persistence.entities.llm.LlmProviderEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LlmProviderRepository implements PanacheRepositoryBase<LlmProviderEntity, UUID> {

    /**
     * Find all active providers
     */
    public Collection<LlmProviderEntity> findAllActive() {
        return find("isActive = true ORDER BY displayName").list();
    }

    /**
     * Find provider by key
     */
    public LlmProviderEntity findByKey(String providerKey) {
        return find("providerKey = ?1", providerKey).firstResult();
    }

    /**
     * Find provider by key (optional)
     */
    public Optional<LlmProviderEntity> findByKeyOptional(String providerKey) {
        return find("providerKey = ?1", providerKey).firstResultOptional();
    }

    /**
     * Find providers that support JSON schema
     */
    public Collection<LlmProviderEntity> findJsonSchemaSupported() {
        return find("isActive = true AND supportsJsonSchema = true ORDER BY displayName").list();
    }

    /**
     * Find providers that support streaming
     */
    public Collection<LlmProviderEntity> findStreamingSupported() {
        return find("isActive = true AND supportsStreaming = true ORDER BY displayName").list();
    }

    /**
     * Search providers by name or description
     */
    public Collection<LlmProviderEntity> search(String query) {
        String searchPattern = "%" + query.toLowerCase() + "%";
        return find(
                "isActive = true AND " +
                        "(LOWER(displayName) LIKE ?1 OR LOWER(providerKey) LIKE ?1 OR LOWER(description) LIKE ?1) " +
                        "ORDER BY displayName",
                searchPattern
        ).list();
    }

    /**
     * Count active providers
     */
    public long countActive() {
        return count("isActive = true");
    }

    /**
     * Activate or deactivate a provider
     */
    public void setActive(UUID providerId, boolean active) {
        update("isActive = ?1, updatedAt = CURRENT_TIMESTAMP WHERE id = ?2", active, providerId);
    }

    /**
     * Check if provider exists by key
     */
    public boolean existsByKey(String providerKey) {
        return count("providerKey = ?1", providerKey) > 0;
    }
}
