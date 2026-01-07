package com.chokmai.common.dto.extraction;

import java.util.List;
import java.util.Map;

public class ClassifyRequest {

    public String file_id;
    public String page_range;
    public String file_name;
    public String mime_type;

    public List<PageClassification> page_classifications;

    public Map<String, String> labels;

    public static class PageClassification {
        public String name;
        public String description;
    }
}
