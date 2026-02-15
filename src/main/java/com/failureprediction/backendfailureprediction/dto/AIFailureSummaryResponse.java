package com.failureprediction.backendfailureprediction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIFailureSummaryResponse {
    private String serviceName;
    private String executiveSummary;
    private String businessImpact;
    private List<String> recommendedActions;
    private String confidence;
    private String rootCause;
    public AIFailureSummaryResponse(
            String serviceName,
            String executiveSummary,
            List<String> recommendedActions,
            String confidence,
            String rootCause) {
        this.serviceName = serviceName;
        this.executiveSummary = executiveSummary;
        this.recommendedActions = recommendedActions;
        this.confidence = confidence;
        this.rootCause=rootCause;
    }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getExecutiveSummary() { return executiveSummary; }
    public void setExecutiveSummary(String executiveSummary) {
        this.executiveSummary = executiveSummary;
    }
    public String getBusinessImpact() { return businessImpact; }
    public void setBusinessImpact(String businessImpact) {
        this.businessImpact = businessImpact;
    }
    public List<String> getRecommendedActions() {
        return recommendedActions;
    }
    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }
    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
}