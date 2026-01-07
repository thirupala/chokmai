package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseDeleteResponse {

    public String message;

    public String code;

    public Long timestamp;

    @JsonProperty("trace_id")
    public String traceId;

    public Object details;
}
