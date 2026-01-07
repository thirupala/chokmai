package com.chokmai.common.dto.extraction;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FileListResponse {

    private List<FileMetadata> items;

    @JsonProperty("has_more")
    private Boolean hasMore;

    @JsonProperty("next_cursor")
    private String nextCursor;

    @JsonProperty("prev_cursor")
    private String prevCursor;

    // Constructors
    public FileListResponse() {}

    // Getters and Setters
    public List<FileMetadata> getItems() {
        return items;
    }

    public void setItems(List<FileMetadata> items) {
        this.items = items;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public String getPrevCursor() {
        return prevCursor;
    }

    public void setPrevCursor(String prevCursor) {
        this.prevCursor = prevCursor;
    }
}