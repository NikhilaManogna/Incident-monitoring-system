package com.failureprediction.backendfailureprediction.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AIFailureSummaryHistoryResponse {
    private Long id;
    private String serviceName;
    private String executiveSummary;
    private String businessImpact;
    private List<String> recommendedActions;
    private String confidence;
    private LocalDateTime generatedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getExecutiveSummary() {
        return executiveSummary;
    }
    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }
    public String getBusinessImpact() {
        return businessImpact;
    }
    public void setBusinessImpact(String businessImpact) {
        this.businessImpact = businessImpact;
    }
    public List<String> getRecommendedActions() {
        return recommendedActions;
    }
    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }
    public String getConfidence() {
        return confidence;
    }
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}