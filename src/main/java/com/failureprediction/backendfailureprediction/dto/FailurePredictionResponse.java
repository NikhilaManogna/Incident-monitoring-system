package com.failureprediction.backendfailureprediction.dto;

public class FailurePredictionResponse {
    private String serviceName;
    private double failureScore;
    private String riskLevel;
    private String action;
    private String explanation;
    private String recommendedAction;
    public FailurePredictionResponse() {
    }
    public FailurePredictionResponse(
            String serviceName,
            double failureScore,
            String riskLevel,
            String action,
            String explanation,
            String recommendedAction) {
        this.serviceName = serviceName;
        this.failureScore = failureScore;
        this.riskLevel = riskLevel;
        this.action = action;
        this.explanation = explanation;
        this.recommendedAction = recommendedAction;
    }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public double getFailureScore() { return failureScore; }
    public void setFailureScore(double failureScore) { this.failureScore = failureScore; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action=action; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation=explanation; }
    public String getRecommendedAction() { return recommendedAction; }
    public void setRecommendedAction(String recommendedAction) { this.recommendedAction=recommendedAction; }
}