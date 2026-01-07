package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record FileUploadResponse(

        @JsonProperty("file_id")
        String fileId,

        @JsonProperty("created_at")
        Instant createdAt
) {}
