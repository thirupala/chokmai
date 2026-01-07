package com.chokmai.common.dto.extraction;

public class PageClassification {

    private String name;
    private String description;

    // Constructors
    public PageClassification() {}

    public PageClassification(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}