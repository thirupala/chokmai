package com.chokmai.domain.projects;

public record CreateProjectRequest(
        String name,
        String description
) {}