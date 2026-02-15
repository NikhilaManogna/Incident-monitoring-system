package com.failureprediction.backendfailureprediction.dto;

public class RiskTrendResponse {
    private String serviceName;
    private String trend;
    private double changePercentage;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getTrend() {
        return trend;
    }
    public void setTrend(String trend) {
        this.trend = trend;
    }
    public double getChangePercentage() {
        return changePercentage;
    }
    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }
}