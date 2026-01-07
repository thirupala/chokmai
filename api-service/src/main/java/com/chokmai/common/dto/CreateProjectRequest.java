package com.chokmai.common.dto;

public record CreateProjectRequest(
        String name,
        String description
) {}