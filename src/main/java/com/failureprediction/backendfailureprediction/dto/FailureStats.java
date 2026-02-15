package com.failureprediction.backendfailureprediction.dto;

import java.util.Map;

public class FailureStats {
    private long totalFailures;
    private long openFailures;
    private long resolvedFailures;
    private long highRiskFailures;
    private Map<String, Long> serviceWiseFailures;
    public FailureStats() {
    }
    public FailureStats(long totalFailures,
                        long openFailures,
                        long resolvedFailures,
                        long highRiskFailures,
                        Map<String, Long> serviceWiseFailures) {
        this.totalFailures = totalFailures;
        this.openFailures = openFailures;
        this.resolvedFailures = resolvedFailures;
        this.highRiskFailures = highRiskFailures;
        this.serviceWiseFailures = serviceWiseFailures;
    }
    public long getTotalFailures() {
        return totalFailures;
    }
    public void setTotalFailures(long totalFailures) {
        this.totalFailures = totalFailures;
    }
    public long getOpenFailures() {
        return openFailures;
    }
    public void setOpenFailures(long openFailures) {
        this.openFailures = openFailures;
    }
    public long getResolvedFailures() {
        return resolvedFailures;
    }
    public void setResolvedFailures(long resolvedFailures) {
        this.resolvedFailures = resolvedFailures;
    }
    public long getHighRiskFailures() {
        return highRiskFailures;
    }
    public void setHighRiskFailures(long highRiskFailures) {
        this.highRiskFailures = highRiskFailures;
    }
    public Map<String, Long> getServiceWiseFailures() {
        return serviceWiseFailures;
    }
    public void setServiceWiseFailures(Map<String, Long> serviceWiseFailures) {
        this.serviceWiseFailures = serviceWiseFailures;
    }
}