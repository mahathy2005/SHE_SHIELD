package com.sheshield.app;

public class SafetyItem {
    private String category;
    private String title;
    private String description;
    private String mediaUrl; // Optional video or image link

    public SafetyItem(String category, String title, String description, String mediaUrl) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
    }

    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getMediaUrl() { return mediaUrl; }
}