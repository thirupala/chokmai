package com.chokmai.persistence.repositories.organization;


import com.chokmai.persistence.entities.organization.OrganizationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrgRepository implements PanacheRepositoryBase<OrganizationEntity, UUID> {

    /**
     * Find organization by name (case-insensitive)
     */
    public Optional<OrganizationEntity> findByName(String name) {
        return find("LOWER(name) = LOWER(?1)", name).firstResultOptional();
    }

    /**
     * Check if organization exists by name
     */
    public boolean existsByName(String name) {
        return count("LOWER(name) = LOWER(?1)", name) > 0;
    }

    /**
     * Find organizations with pagination
     */
    public List<OrganizationEntity> findPaginated(int pageIndex, int pageSize) {
        return findAll(Sort.by("createdAt").descending())
                .page(Page.of(pageIndex, pageSize))
                .list();
    }

    /**
     * Search organizations by name pattern
     */
    public List<OrganizationEntity> searchByName(String namePattern) {
        return list("LOWER(name) LIKE LOWER(?1)", "%" + namePattern + "%");
    }

    /**
     * Count total organizations
     */
    public long countAll() {
        return count();
    }

    /**
     * Find organizations created after a specific instant
     */
    public List<OrganizationEntity> findRecentOrganizations(int limit) {
        return findAll(Sort.by("createdAt").descending())
                .page(Page.ofSize(limit))
                .list();
    }
}
