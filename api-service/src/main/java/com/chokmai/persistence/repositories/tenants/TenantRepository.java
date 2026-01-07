package com.chokmai.persistence.repositories.tenants;

import com.chokmai.persistence.entities.tenants.TenantEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TenantRepository
        implements PanacheRepository<TenantEntity> {

    /**
     * Find tenant by id
     */
    public Optional<TenantEntity> findById(UUID tenantId) {
        return find("id", tenantId).firstResultOptional();
    }

    /**
     * Find tenant by unique key (e.g., slug or externalId)
     */
    public Optional<TenantEntity> findByKey(String key) {
        return find("key", key).firstResultOptional();
    }

    /**
     * Check if tenant already exists by key
     */
    public boolean existsByKey(String key) {
        return count("key", key) > 0;
    }
}
