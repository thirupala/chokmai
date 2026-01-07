package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseStatus {

    @JsonProperty("parse_id")
    public String parseId;

    public String status;

    @JsonProperty("created_at")
    public Instant createdAt;

    @JsonProperty("dataset_id")
    public String datasetId;

    @JsonProperty("parsed_pages_count")
    public Integer parsedPagesCount;

    @JsonProperty("total_pages")
    public Integer totalPages;

    public String error;

    public List<Object> chunks;

    @JsonProperty("structured_data")
    public Object structuredData;

    @JsonProperty("finished_at")
    public Instant finishedAt;

    public Map<String, Object> labels;

    public String message_update;
}
