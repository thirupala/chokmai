package com.chokmai.domain.llm;

import java.util.Map;

/**
 * Request to create an LLM extraction schema.
 * Schema definition is intentionally opaque JSON.
 */
public record CreateSchemaRequest(
        String name,
        String description,
        String modelProvider,   // openai, anthropic, azure-openai, etc
        String modelName,       // gpt-4o, claude-3, etc
        Map<String, Object> schemaDefinition
) {}
