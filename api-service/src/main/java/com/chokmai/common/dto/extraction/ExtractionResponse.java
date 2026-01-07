package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtractionResponse {

    @JsonProperty("parse_id")
    private String parseId; // TensorLake job identifier

    @JsonProperty("created_at")
    private String createdAt;

    // Constructors
    public ExtractionResponse() {}

    public ExtractionResponse(String parseId, String createdAt) {
        this.parseId = parseId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getParseId() {
        return parseId;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}