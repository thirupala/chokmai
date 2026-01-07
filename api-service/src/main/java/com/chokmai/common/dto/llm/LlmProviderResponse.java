package com.chokmai.common.dto.llm;

public  class LlmProviderResponse {
    public java.util.UUID id;
    public String providerKey;
    public String displayName;
    public String description;
    public Boolean supportsJsonSchema;
    public Boolean supportsStreaming;
}
