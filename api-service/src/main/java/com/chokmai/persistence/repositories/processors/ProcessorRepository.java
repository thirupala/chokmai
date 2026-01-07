package com.chokmai.persistence.repositories.processors;

import com.chokmai.persistence.entities.processors.ProcessorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class ProcessorRepository implements PanacheRepositoryBase<ProcessorEntity, UUID> {

    /**
     * Find processor by ID
     * Returns null if not found
     */
    public ProcessorEntity findById(UUID id) {
        return find("id", id).firstResult();
    }

    /**
     * Find processor by ID with Optional
     * Returns Optional.empty() if not found
     */
    public Optional<ProcessorEntity> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    /**
     * Find processor by ID with project loaded (optimized query)
     */
    public Optional<ProcessorEntity> findByIdWithProject(UUID id) {
        return find("SELECT p FROM Processor p " +
                "LEFT JOIN FETCH p.project " +
                "WHERE p.id = ?1", id).firstResultOptional();
    }

    /**
     * Find all processors for a project
     */
    public List<ProcessorEntity> findByProject(UUID projectId) {
        return find("project.id", Sort.descending("createdAt"), projectId).list();
    }

    /**
     * Find all processors for a project with pagination
     */
    public List<ProcessorEntity> findByProject(UUID projectId, int page, int size) {
        return find("project.id", Sort.descending("createdAt"), projectId)
                .page(page, size)
                .list();
    }

    /**
     * Find processor by name within a project
     */
    public ProcessorEntity findByProjectAndName(UUID projectId, String name) {
        return find("project.id = ?1 AND name = ?2", projectId, name).firstResult();
    }

    /**
     * Find all processors by tenant
     */
    public List<ProcessorEntity> findByTenant(UUID tenantId) {
        return find("project.tenantId", Sort.descending("createdAt"), tenantId).list();
    }

    /**
     * Check if processor name exists in project
     */
    public boolean existsByProjectAndName(UUID projectId, String name) {
        return count("project.id = ?1 AND name = ?2", projectId, name) > 0;
    }

    /**
     * Count processors in a project
     */
    public long countByProject(UUID projectId) {
        return count("project.id", projectId);
    }

    /**
     * Find active processors (if you have an active flag)
     */
    public List<ProcessorEntity> findActiveByProject(UUID projectId) {
        return find("project.id = ?1 AND active = true", Sort.descending("createdAt"), projectId).list();
    }

    /**
     * Search processors by name (case-insensitive)
     */
    public List<ProcessorEntity> searchByName(String searchTerm) {
        return find("LOWER(name) LIKE LOWER(?1)", Sort.ascending("name"), "%" + searchTerm + "%").list();
    }

    /**
     * Search processors by name within a project
     */
    public List<ProcessorEntity> searchByNameInProject(UUID projectId, String searchTerm) {
        return find("project.id = ?1 AND LOWER(name) LIKE LOWER(?2)",
                Sort.ascending("name"), projectId, "%" + searchTerm + "%").list();
    }

    /**
     * Delete all processors in a project
     */
    public long deleteByProject(UUID projectId) {
        return delete("project.id", projectId);
    }

}