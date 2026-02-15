package com.failureprediction.backendfailureprediction.dto;

import java.util.List;

public class RiskForecastResponse {
    private String serviceName;
    private String predictedRisk;
    private double confidence;
    private List<String> reasoning;
    private List<String> recommendedPreventiveActions;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getPredictedRisk() {
        return predictedRisk;
    }
    public void setPredictedRisk(String predictedRisk) {
        this.predictedRisk = predictedRisk;
    }
    public double getConfidence() {
        return confidence;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    public List<String> getReasoning() {
        return reasoning;
    }
    public void setReasoning(List<String> reasoning) {
        this.reasoning = reasoning;
    }
    public List<String> getRecommendedPreventiveActions() {
        return recommendedPreventiveActions;
    }
    public void setRecommendedPreventiveActions(List<String> recommendedPreventiveActions) {
        this.recommendedPreventiveActions = recommendedPreventiveActions;
    }
}