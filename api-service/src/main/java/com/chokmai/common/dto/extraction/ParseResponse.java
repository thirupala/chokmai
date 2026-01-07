package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record ParseResponse(

        @JsonProperty("parse_id")
        String parseId,

        @JsonProperty("created_at")
        Instant createdAt
) {}
