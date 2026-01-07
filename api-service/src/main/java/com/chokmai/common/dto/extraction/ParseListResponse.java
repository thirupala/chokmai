package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseListResponse {

    public List<ParseStatusResponse> items;

    @JsonProperty("has_more")
    public boolean hasMore;

    @JsonProperty("next_cursor")
    public String nextCursor;

    @JsonProperty("prev_cursor")
    public String prevCursor;
}
