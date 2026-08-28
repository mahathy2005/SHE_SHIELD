package com.sheshield.app;

public class SafetyCardModel {
    private String category;
    private String title;
    private String description;
    private int strokeColor;
    private int backgroundColor;

    // Constructor for Knowledge cards
    public SafetyCardModel(String category, String title, String description, int strokeColor, int backgroundColor) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.strokeColor = strokeColor;
        this.backgroundColor = backgroundColor;
    }

    // Constructor for Learn cards (default colors)
    public SafetyCardModel(String category, String title, String description) {
        this(category, title, description, 0xFFE0E0E0, 0xFFFFFFFF);
    }

    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getStrokeColor() { return strokeColor; }
    public int getBackgroundColor() { return backgroundColor; }
}