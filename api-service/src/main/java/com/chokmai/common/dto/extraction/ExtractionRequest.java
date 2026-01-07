package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class ExtractionRequest {

    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("page_range")
    private String pageRange; // e.g., "1-5" or "all"

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("mime_type")
    private String mimeType;

    @JsonProperty("structured_extraction_options")
    private List<StructuredExtractionOption> structuredExtractionOptions;

    private Map<String, String> labels;

    // Constructors
    public ExtractionRequest() {}

    // Getters and Setters
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getPageRange() {
        return pageRange;
    }

    public void setPageRange(String pageRange) {
        this.pageRange = pageRange;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public List<StructuredExtractionOption> getStructuredExtractionOptions() {
        return structuredExtractionOptions;
    }

    public void setStructuredExtractionOptions(List<StructuredExtractionOption> structuredExtractionOptions) {
        this.structuredExtractionOptions = structuredExtractionOptions;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }
}