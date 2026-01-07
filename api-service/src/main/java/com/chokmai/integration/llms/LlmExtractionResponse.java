package com.chokmai.integration.llms;

import com.fasterxml.jackson.databind.JsonNode;

public class LlmExtractionResponse {
    public JsonNode extractedData;
    public int inputTokens;
    public int outputTokens;
    public long requestTimeMs;
    public String modelUsed;
    public boolean success;
    public String errorMessage;
}