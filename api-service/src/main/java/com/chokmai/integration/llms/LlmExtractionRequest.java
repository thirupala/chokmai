package com.chokmai.integration.llms;

import com.fasterxml.jackson.databind.JsonNode;

public class LlmExtractionRequest {
    public String modelKey;
    public String prompt;
    public JsonNode jsonSchema;
    public String documentText;
    public byte[] documentImage; // for vision models
    public Double temperature;
    public Integer maxTokens;

    // Builder pattern
    public static class Builder {
        private final LlmExtractionRequest request = new LlmExtractionRequest();

        public Builder modelKey(String modelKey) {
            request.modelKey = modelKey;
            return this;
        }

        public Builder prompt(String prompt) {
            request.prompt = prompt;
            return this;
        }

        public Builder jsonSchema(JsonNode schema) {
            request.jsonSchema = schema;
            return this;
        }

        public Builder documentText(String text) {
            request.documentText = text;
            return this;
        }

        public Builder documentImage(byte[] image) {
            request.documentImage = image;
            return this;
        }

        public Builder temperature(Double temperature) {
            request.temperature = temperature;
            return this;
        }

        public Builder maxTokens(Integer maxTokens) {
            request.maxTokens = maxTokens;
            return this;
        }

        public LlmExtractionRequest build() {
            return request;
        }
    }
}
