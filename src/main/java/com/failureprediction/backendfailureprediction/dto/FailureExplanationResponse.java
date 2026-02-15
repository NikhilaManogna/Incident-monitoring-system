package com.failureprediction.backendfailureprediction.dto;

public class FailureExplanationResponse {
    private String summary;
    private String probableCause;
    private String recommendedAction;
    public FailureExplanationResponse(String summary,
                                      String probableCause,
                                      String recommendedAction) {
        this.summary = summary;
        this.probableCause = probableCause;
        this.recommendedAction = recommendedAction;
    }
    public String getSummary() {
        return summary;
    }
    public String getProbableCause() {
        return probableCause;
    }
    public String getRecommendedAction() {
        return recommendedAction;
    }
}