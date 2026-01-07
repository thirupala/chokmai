package com.chokmai.api.llm;


import com.chokmai.common.dto.llm.LlmModelResponse;
import com.chokmai.common.dto.llm.LlmProviderResponse;
import com.chokmai.persistence.entities.llm.LlmModelEntity;
import com.chokmai.persistence.entities.llm.LlmProviderEntity;
import com.chokmai.persistence.repositories.llms.LlmModelRepository;
import com.chokmai.persistence.repositories.llms.LlmProviderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/llm")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
        @APIResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
        @APIResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
        @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound"),
        @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
})
public class LlmResource {

    @Inject
    LlmProviderRepository llmProviderRepository;

    @Inject
    LlmModelRepository llmModelRepository;

    @GET
    @Path("/providers")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public List<LlmProviderResponse> listProviders() {
        return llmProviderRepository.findAllActive()
                .stream()
                .map(this::toProviderResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/providers/{providerKey}/models")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public List<LlmModelResponse> listModelsByProvider(@PathParam("providerKey") String providerKey) {
        LlmProviderEntity provider = llmProviderRepository.findByKey(providerKey);
        if (provider == null) {
            throw new NotFoundException("Provider not found");
        }

        return llmModelRepository.findByProvider(provider.id)
                .stream()
                .map(this::toModelResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/models")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public List<LlmModelResponse> listAllModels() {
        return llmModelRepository.findAllActive()
                .stream()
                .map(this::toModelResponse)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/models/{modelId}")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Audit logs returned"),
    })
    public LlmModelResponse getModel(@PathParam("modelId") String modelId) {
        LlmModelEntity model = llmModelRepository.findById(java.util.UUID.fromString(modelId));
        if (model == null) {
            throw new NotFoundException("Model not found");
        }
        return toModelResponse(model);
    }

    private LlmProviderResponse toProviderResponse(LlmProviderEntity entity) {
        LlmProviderResponse response = new LlmProviderResponse();
        response.id = entity.id;
        response.providerKey = entity.providerKey;
        response.displayName = entity.displayName;
        response.description = entity.description;
        response.supportsJsonSchema = entity.supportsJsonSchema;
        response.supportsStreaming = entity.supportsStreaming;
        return response;
    }

    private LlmModelResponse toModelResponse(LlmModelEntity entity) {
        LlmModelResponse response = new LlmModelResponse();
        response.id = entity.id;
        response.providerKey = entity.provider.providerKey;
        response.providerName = entity.provider.displayName;
        response.modelKey = entity.modelKey;
        response.displayName = entity.displayName;
        response.description = entity.description;
        response.contextWindow = entity.contextWindow;
        response.maxOutputTokens = entity.maxOutputTokens;
        response.supportsVision = entity.supportsVision;
        response.supportsFunctionCalling = entity.supportsFunctionCalling;
        response.costPer1kInputTokens = entity.costPer1kInputTokens;
        response.costPer1kOutputTokens = entity.costPer1kOutputTokens;
        response.capabilities = entity.capabilities;
        return response;
    }

}