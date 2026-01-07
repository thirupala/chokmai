package com.chokmai.integration.llms;

import com.chokmai.api.integration.extraction.ExtractionRestClient;
import com.chokmai.common.dto.extraction.ExtractionRequest;
import com.chokmai.common.dto.extraction.ExtractionResponse;
import com.chokmai.common.dto.extraction.StructuredExtractionOption;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TensorLakeLlmProvider implements LlmProvider {

    @Inject
    @RestClient
    ExtractionRestClient extractionClient;

    @ConfigProperty(name = "tensorlake.api.token")
    String tensorLakeToken;

    @Override
    public String getProviderKey() {
        return "tensorlake";
    }
    @Inject
    private String auth() {
        return "Bearer " + tensorLakeToken;
    }

    @Override
    public LlmExtractionResponse extract(LlmExtractionRequest request) {
        long startTime = System.currentTimeMillis();

        try {
            // Build structured extraction option with selected model
            StructuredExtractionOption extractionOption = new StructuredExtractionOption();
            extractionOption.setSchemaName("custom_extraction");
            extractionOption.setJsonSchema(String.valueOf(request.jsonSchema));
            extractionOption.setPrompt(request.prompt);
            extractionOption.setModelProvider(getModelProvider(request.modelKey));
            extractionOption.setProvideCitations(true);
            extractionOption.setSkipOcr(false);

            // Create extraction request for TensorLake
            ExtractionRequest extractionRequest = new ExtractionRequest();
            extractionRequest.setFileId(extractFileIdFromContext()); // You'll need to pass this
            extractionRequest.setStructuredExtractionOptions(List.of(extractionOption));

            // Call TensorLake
            ExtractionResponse response = extractionClient.createExtractionJob(auth(),extractionRequest);

            // Build response
            LlmExtractionResponse llmResponse = new LlmExtractionResponse();
            llmResponse.success = true;
            llmResponse.modelUsed = request.modelKey;
            llmResponse.requestTimeMs = System.currentTimeMillis() - startTime;
            // Note: TensorLake will send token counts via webhook

            return llmResponse;

        } catch (Exception e) {
            LlmExtractionResponse errorResponse = new LlmExtractionResponse();
            errorResponse.success = false;
            errorResponse.errorMessage = e.getMessage();
            errorResponse.requestTimeMs = System.currentTimeMillis() - startTime;
            return errorResponse;
        }
    }

    @Override
    public boolean supportsJsonSchema() {
        return true;
    }

    @Override
    public Map<String, Object> getCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("supports_pdf", true);
        capabilities.put("supports_images", true);
        capabilities.put("supports_vision", true);
        capabilities.put("max_pages", 1000);
        return capabilities;
    }

    private String getModelProvider(String modelKey) {
        // Map model keys to TensorLake providers
        if (modelKey.startsWith("gpt-")) return "openai";
        if (modelKey.startsWith("claude-")) return "anthropic";
        if (modelKey.startsWith("gemini-")) return "google";
        return "tensorlake"; // default
    }

    private String extractFileIdFromContext() {
        // This should be passed through the request context
        // For now, throw an exception to remind implementation
        throw new UnsupportedOperationException("File ID must be provided in request context");
    }
}
