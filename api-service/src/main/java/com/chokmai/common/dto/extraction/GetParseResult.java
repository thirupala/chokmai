package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetParseResult(

        @JsonProperty("parse_id")
        String parseId,

        String status,

        @JsonProperty("created_at")
        Instant createdAt,

        @JsonProperty("dataset_id")
        String datasetId,

        @JsonProperty("parsed_pages_count")
        Integer parsedPagesCount,

        @JsonProperty("total_pages")
        Integer totalPages,

        String error,

        Object pages,

        List<Object> chunks,

        @JsonProperty("structured_data")
        Object structuredData,

        @JsonProperty("page_classes")
        Object pageClasses,

        @JsonProperty("pdf_base64")
        String pdfBase64,

        @JsonProperty("tasks_completed_count")
        Integer tasksCompletedCount,

        @JsonProperty("tasks_total_count")
        Integer tasksTotalCount,

        @JsonProperty("finished_at")
        Instant finishedAt,

        Map<String, Object> labels,

        Object options,

        Object usage,

        @JsonProperty("message_update")
        String messageUpdate
) {}
