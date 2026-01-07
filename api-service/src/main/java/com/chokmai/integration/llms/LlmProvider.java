package com.chokmai.integration.llms;

import java.util.Map;

public interface LlmProvider {
    String getProviderKey();

    LlmExtractionResponse extract(LlmExtractionRequest request);

    boolean supportsJsonSchema();

    Map<String, Object> getCapabilities();
}