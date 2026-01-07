package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class StructuredExtractionOption {

    @JsonProperty("schema_name")
    private String schemaName;

    @JsonProperty("json_schema")
    private String jsonSchema; // The actual JSON schema object from processor

    @JsonProperty("skip_ocr")
    private Boolean skipOcr;

    private String prompt; // Optional additional prompt

    @JsonProperty("model_provider")
    private String modelProvider; // "tensorlake" or other

    @JsonProperty("partition_strategy")
    private String partitionStrategy; // "none", "page", etc.

    @JsonProperty("page_classes")
    private List<String> pageClasses;

    @JsonProperty("provide_citations")
    private Boolean provideCitations;

    // Constructors
    public StructuredExtractionOption() {}

    // Getters and Setters
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getJsonSchema() {
        return jsonSchema;
    }

    public void setJsonSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    public Boolean getSkipOcr() {
        return skipOcr;
    }

    public void setSkipOcr(Boolean skipOcr) {
        this.skipOcr = skipOcr;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getModelProvider() {
        return modelProvider;
    }

    public void setModelProvider(String modelProvider) {
        this.modelProvider = modelProvider;
    }

    public String getPartitionStrategy() {
        return partitionStrategy;
    }

    public void setPartitionStrategy(String partitionStrategy) {
        this.partitionStrategy = partitionStrategy;
    }

    public List<String> getPageClasses() {
        return pageClasses;
    }

    public void setPageClasses(List<String> pageClasses) {
        this.pageClasses = pageClasses;
    }

    public Boolean getProvideCitations() {
        return provideCitations;
    }

    public void setProvideCitations(Boolean provideCitations) {
        this.provideCitations = provideCitations;
    }
}