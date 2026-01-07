package com.chokmai.domain.projects;

import com.chokmai.persistence.entities.projects.ProjectEntity;

import java.time.Instant;
import java.util.UUID;

public record ProjectDto(
        UUID id,
        String name,
        String description,
        UUID tenantId, Instant createdAt
) {
    public static ProjectDto fromEntity(ProjectEntity e) {
        return new ProjectDto(
                e.id,
                e.name,
                e.description,
                e.tenantId, e.createdAt
        );
    }
}
