package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassifyResponse {
    public String status;
    public Object result;
}

