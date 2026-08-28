package com.sheshield.app;

public class KnowledgeModel {
    private String title;
    private String description;
    private String category;

    public KnowledgeModel(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
}