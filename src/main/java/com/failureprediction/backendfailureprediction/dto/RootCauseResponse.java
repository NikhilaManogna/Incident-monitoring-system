package com.failureprediction.backendfailureprediction.dto;

import java.util.List;

public class RootCauseResponse {
    private String serviceName;
    private String rootCause;
    private String confidence;
    private List<String> observations;
    private String recommendedFix;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getRootCause() {
        return rootCause;
    }
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
    public String getConfidence() {
        return confidence;
    }
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    public List<String> getObservations() {
        return observations;
    }
    public void setObservations(List<String> observations) {
        this.observations = observations;
    }
    public String getRecommendedFix() {
        return recommendedFix;
    }
    public void setRecommendedFix(String recommendedFix) {
        this.recommendedFix = recommendedFix;
    }
}