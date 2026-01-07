package com.chokmai.common.dto.llm;

public  class LlmModelResponse {
    public java.util.UUID id;
    public String providerKey;
    public String providerName;
    public String modelKey;
    public String displayName;
    public String description;
    public Integer contextWindow;
    public Integer maxOutputTokens;
    public Boolean supportsVision;
    public Boolean supportsFunctionCalling;
    public java.math.BigDecimal costPer1kInputTokens;
    public java.math.BigDecimal costPer1kOutputTokens;
    public java.util.Map<String, Object> capabilities;
}
