package com.chokmai.persistence.repositories.projects;

import com.chokmai.persistence.entities.projects.ProjectEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProjectRepository
        implements PanacheRepository<ProjectEntity> {

    /**
     * List projects for a tenant (excluding soft-deleted)
     */
    public List<ProjectEntity> findByTenant(UUID tenantId) {
        return find(
                "tenantId = ?1 and deleted = false",
                tenantId
        ).list();
    }

    /**
     * Find project by id scoped to tenant
     */
    public Optional<ProjectEntity> findByIdAndTenant(
            UUID projectId,
            UUID tenantId) {

        return find(
                "id = ?1 and tenantId = ?2 and deleted = false",
                projectId,
                tenantId
        ).firstResultOptional();
    }

    /**
     * Check if project name already exists for tenant
     */
    public boolean existsByName(UUID tenantId, String name) {
        return count(
                "tenantId = ?1 and name = ?2 and deleted = false",
                tenantId,
                name
        ) > 0;
    }

    public Collection<ProjectEntity> findForTenant(UUID tenantId) {
        return find("tenantId", tenantId)
                .list();

    }

}
