package com.chokmai.common.dto.extraction;


import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrichmentOptions {

    @JsonProperty("table_summarization")
    private Boolean tableSummarization;

    @JsonProperty("table_summarization_prompt")
    private String tableSummarizationPrompt;

    @JsonProperty("figure_summarization")
    private Boolean figureSummarization;

    @JsonProperty("figure_summarization_prompt")
    private String figureSummarizationPrompt;

    @JsonProperty("include_full_page_image")
    private Boolean includeFullPageImage;

    // Constructors
    public EnrichmentOptions() {}

    // Getters and Setters
    public Boolean getTableSummarization() {
        return tableSummarization;
    }

    public void setTableSummarization(Boolean tableSummarization) {
        this.tableSummarization = tableSummarization;
    }

    public String getTableSummarizationPrompt() {
        return tableSummarizationPrompt;
    }

    public void setTableSummarizationPrompt(String tableSummarizationPrompt) {
        this.tableSummarizationPrompt = tableSummarizationPrompt;
    }

    public Boolean getFigureSummarization() {
        return figureSummarization;
    }

    public void setFigureSummarization(Boolean figureSummarization) {
        this.figureSummarization = figureSummarization;
    }

    public String getFigureSummarizationPrompt() {
        return figureSummarizationPrompt;
    }

    public void setFigureSummarizationPrompt(String figureSummarizationPrompt) {
        this.figureSummarizationPrompt = figureSummarizationPrompt;
    }

    public Boolean getIncludeFullPageImage() {
        return includeFullPageImage;
    }

    public void setIncludeFullPageImage(Boolean includeFullPageImage) {
        this.includeFullPageImage = includeFullPageImage;
    }
}