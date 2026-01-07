package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ParsingOptions {

    @JsonProperty("table_output_mode")
    private String tableOutputMode; // "html", "markdown", etc.

    @JsonProperty("table_parsing_format")
    private String tableParsingFormat; // "tsr"

    @JsonProperty("chunking_strategy")
    private String chunkingStrategy; // "none", "semantic", etc.

    @JsonProperty("signature_detection")
    private Boolean signatureDetection;

    @JsonProperty("remove_strikethrough_lines")
    private Boolean removeStrikethroughLines;

    @JsonProperty("skew_detection")
    private Boolean skewDetection;

    @JsonProperty("disable_layout_detection")
    private Boolean disableLayoutDetection;

    @JsonProperty("ignore_sections")
    private List<String> ignoreSections;

    @JsonProperty("cross_page_header_detection")
    private Boolean crossPageHeaderDetection;

    @JsonProperty("include_images")
    private Boolean includeImages;

    @JsonProperty("barcode_detection")
    private Boolean barcodeDetection;

    @JsonProperty("ocr_model")
    private String ocrModel; // "model01", etc.

    // Constructors
    public ParsingOptions() {}

    // Getters and Setters
    public String getTableOutputMode() {
        return tableOutputMode;
    }

    public void setTableOutputMode(String tableOutputMode) {
        this.tableOutputMode = tableOutputMode;
    }

    public String getTableParsingFormat() {
        return tableParsingFormat;
    }

    public void setTableParsingFormat(String tableParsingFormat) {
        this.tableParsingFormat = tableParsingFormat;
    }

    public String getChunkingStrategy() {
        return chunkingStrategy;
    }

    public void setChunkingStrategy(String chunkingStrategy) {
        this.chunkingStrategy = chunkingStrategy;
    }

    public Boolean getSignatureDetection() {
        return signatureDetection;
    }

    public void setSignatureDetection(Boolean signatureDetection) {
        this.signatureDetection = signatureDetection;
    }

    public Boolean getRemoveStrikethroughLines() {
        return removeStrikethroughLines;
    }

    public void setRemoveStrikethroughLines(Boolean removeStrikethroughLines) {
        this.removeStrikethroughLines = removeStrikethroughLines;
    }

    public Boolean getSkewDetection() {
        return skewDetection;
    }

    public void setSkewDetection(Boolean skewDetection) {
        this.skewDetection = skewDetection;
    }

    public Boolean getDisableLayoutDetection() {
        return disableLayoutDetection;
    }

    public void setDisableLayoutDetection(Boolean disableLayoutDetection) {
        this.disableLayoutDetection = disableLayoutDetection;
    }

    public List<String> getIgnoreSections() {
        return ignoreSections;
    }

    public void setIgnoreSections(List<String> ignoreSections) {
        this.ignoreSections = ignoreSections;
    }

    public Boolean getCrossPageHeaderDetection() {
        return crossPageHeaderDetection;
    }

    public void setCrossPageHeaderDetection(Boolean crossPageHeaderDetection) {
        this.crossPageHeaderDetection = crossPageHeaderDetection;
    }

    public Boolean getIncludeImages() {
        return includeImages;
    }

    public void setIncludeImages(Boolean includeImages) {
        this.includeImages = includeImages;
    }

    public Boolean getBarcodeDetection() {
        return barcodeDetection;
    }

    public void setBarcodeDetection(Boolean barcodeDetection) {
        this.barcodeDetection = barcodeDetection;
    }

    public String getOcrModel() {
        return ocrModel;
    }

    public void setOcrModel(String ocrModel) {
        this.ocrModel = ocrModel;
    }
}