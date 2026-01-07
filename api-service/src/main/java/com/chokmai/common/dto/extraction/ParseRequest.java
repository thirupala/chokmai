package com.chokmai.common.dto.extraction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseRequest {

    // Core file info
    public String file_id;
    public String page_range;
    public String file_name;
    public String mime_type;

    // Parsing options
    public ParsingOptions parsing_options;

    // Structured extraction (schemas)
    public List<StructuredExtractionOption> structured_extraction_options;

    // Page classification
    public List<PageClassification> page_classifications;

    // Enrichment options
    public EnrichmentOptions enrichment_options;

    // Arbitrary labels
    public Map<String, String> labels;

    /* =========================
       NESTED CONFIG OBJECTS
       ========================= */

    public static class ParsingOptions {
        public String table_output_mode;
        public String table_parsing_format;
        public String chunking_strategy;
        public boolean signature_detection;
        public boolean remove_strikethrough_lines;
        public boolean skew_detection;
        public boolean disable_layout_detection;
        public List<String> ignore_sections;
        public boolean cross_page_header_detection;
        public boolean include_images;
        public boolean barcode_detection;
        public String ocr_model;
    }

    public static class StructuredExtractionOption {
        public String schema_name;
        public Object json_schema;
        public boolean skip_ocr;
        public String prompt;
        public String model_provider;
        public String partition_strategy;
        public List<String> page_classes;
        public boolean provide_citations;
    }

    public static class PageClassification {
        public String name;
        public String description;
    }

    public static class EnrichmentOptions {
        public boolean table_summarization;
        public String table_summarization_prompt;
        public boolean figure_summarization;
        public String figure_summarization_prompt;
        public boolean include_full_page_image;
    }
}
