package com.chokmai.domain.projects;

import com.chokmai.observability.AuditService;
import com.chokmai.persistence.entities.projects.ProjectEntity;
import com.chokmai.persistence.repositories.projects.ProjectRepository;
import com.chokmai.security.AuthContext;
import com.chokmai.security.TenantContext;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ProjectService implements PanacheRepositoryBase<ProjectEntity, UUID>
{

    @Inject
    ProjectRepository projectRepository;

    @Inject
    AuthContext authContext;

    @Inject
    TenantContext tenantContext;

    @Inject
    AuditService auditService;

    public List<ProjectEntity> findForTenant(UUID tenantId) {
        return find("tenantId", tenantId).list();
    }

    /**
     * List projects for a tenant (String tenantId from REST layer).
     */
    public List<ProjectDto> list(String tenantId) {
        UUID tenantUuid = UUID.fromString(tenantId);
        return list(tenantUuid);
    }

    /**
     * Create a new project under the current tenant
     */
    @Transactional
    public UUID create(String tenantId, CreateProjectRequest request) {
        ProjectEntity project = new ProjectEntity();
        project.id = UUID.randomUUID();
        project.tenantId = UUID.fromString(tenantId);
        project.name = request.name();
        project.description = request.description();
        project.createdAt = Instant.now();

        projectRepository.persist(project);

        auditService.record(
                authContext.actor(),
                UUID.fromString(tenantId),
                "PROJECT_CREATE",
                project.id.toString(),
                "SUCCESS"
        );

        return project.id;
    }

    /**
     * List projects for the current tenant
     */
    public List<ProjectDto> list() {
        UUID tenantId = UUID.fromString(tenantContext.tenantId());

        return projectRepository
                .findByTenant(tenantId)
                .stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    /**
     * Update project metadata
     */
    @Transactional
    public void update(UUID projectId, UpdateProjectRequest request) {
        UUID tenantId = UUID.fromString(tenantContext.tenantId());

        ProjectEntity project =
                projectRepository.findByIdAndTenant(projectId, tenantId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Project not found"));

        project.name = request.name();
        project.description = request.description();

        auditService.record(
                authContext.actor(),
                tenantId,
                "PROJECT_UPDATE",
                projectId.toString(),
                "SUCCESS"
        );
    }

    /**
     * Delete project (soft delete recommended)
     */
    @Transactional
    public void delete(UUID projectId) {
        UUID tenantId = UUID.fromString(tenantContext.tenantId());

        ProjectEntity project =
                projectRepository.findByIdAndTenant(projectId, tenantId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Project not found"));

        projectRepository.delete(project);

        auditService.record(
                authContext.actor(),
                tenantId,
                "PROJECT_DELETE",
                projectId.toString(),
                "SUCCESS"
        );
    }
    public List<ProjectDto> list(UUID tenantId) {
        return projectRepository.findForTenant(tenantId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ProjectDto toDto(ProjectEntity e) {
        return new ProjectDto(
                e.id,
                e.name,
                e.description,
                e.tenantId,
                e.createdAt
        );
    }
}
