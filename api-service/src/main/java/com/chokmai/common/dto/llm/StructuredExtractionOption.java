package com.chokmai.common.dto.llm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class StructuredExtractionOption {
    @JsonProperty("schema_name")
    public String schemaName;

    @JsonProperty("json_schema")
    public JsonNode jsonSchema;

    @JsonProperty("skip_ocr")
    public Boolean skipOcr = false;

    public String prompt;

    @JsonProperty("model_provider")
    public String modelProvider; // "openai", "anthropic", "google", etc.

    // NEW: Allow user to specify exact model
    @JsonProperty("model_name")
    public String modelName; // "gpt-4o", "claude-3-opus", etc.

    @JsonProperty("partition_strategy")
    public String partitionStrategy;

    @JsonProperty("page_classes")
    public List<String> pageClasses;

    @JsonProperty("provide_citations")
    public Boolean provideCitations = true;

    // NEW: Additional LLM parameters
    public Double temperature;

    @JsonProperty("max_tokens")
    public Integer maxTokens;

    @JsonProperty("top_p")
    public Double topP;
}
