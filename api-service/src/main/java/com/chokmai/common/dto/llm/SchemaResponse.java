    package com.chokmai.common.dto.llm;

import java.time.Instant;
import java.util.UUID;

/**
 * API-safe representation of an LLM schema.
 */
public record SchemaResponse(
        UUID id,
        String name,
        String description,
        String modelProvider,
        String modelName,
        Instant createdAt
) {}
