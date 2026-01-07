package com.chokmai.persistence.repositories.llms;

import com.chokmai.persistence.entities.llm.LlmSchemaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LlmSchemaRepository
        implements PanacheRepository<LlmSchemaEntity> {

    /**
     * Find all schemas for a tenant (excluding soft-deleted)
     */
    public List<LlmSchemaEntity> findByTenant(UUID tenantId) {
        return find(
                "tenantId = ?1 and deleted = false",
                tenantId
        ).list();
    }

    /**
     * Find schema by id scoped to tenant
     */
    public Optional<LlmSchemaEntity> findByIdAndTenant(
            UUID id,
            UUID tenantId) {

        return find(
                "id = ?1 and tenantId = ?2 and deleted = false",
                id,
                tenantId
        ).firstResultOptional();
    }

    /**
     * Check for duplicate schema name per tenant
     */
    public boolean existsByName(UUID tenantId, String name) {
        return count(
                "tenantId = ?1 and name = ?2 and deleted = false",
                tenantId,
                name
        ) > 0;
    }
}
