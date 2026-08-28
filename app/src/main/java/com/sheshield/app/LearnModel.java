package com.sheshield.app;

public class LearnModel {
    private String moveTitle;
    private String instructions;
    private String youtubeVideoId; // e.g., "KVpxP3ZZtAc"

    public LearnModel(String moveTitle, String instructions, String youtubeVideoId) {
        this.moveTitle = moveTitle;
        this.instructions = instructions;
        this.youtubeVideoId = youtubeVideoId;
    }

    // Getters
    public String getMoveTitle() { return moveTitle; }
    public String getInstructions() { return instructions; }
    public String getYoutubeVideoId() { return youtubeVideoId; }
}