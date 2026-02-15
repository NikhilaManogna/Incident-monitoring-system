package com.failureprediction.backendfailureprediction.dto;

import java.util.Map;

public class DashboardResponse {
    private long totalFailures;
    private long openFailures;
    private long resolvedFailures;
    private long todayFailures;
    private long last7DaysFailures;
    private long thisMonthFailures;
    private Map<String, Long> serviceWise;
    private Map<String, Long> riskWise;

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
    public Map<String, Long> getServiceWise() {
        return serviceWise;
    }
    public void setServiceWise(Map<String, Long> serviceWise) {
        this.serviceWise = serviceWise;
    }
    public Map<String, Long> getRiskWise() {
        return riskWise;
    }
    public void setRiskWise(Map<String, Long> riskWise) {
        this.riskWise = riskWise;
    }
    public long getTodayFailures() {
        return todayFailures;
    }
    public void setTodayFailures(long todayFailures) {
        this.todayFailures = todayFailures;
    }
    public long getLast7DaysFailures() {
        return last7DaysFailures;
    }
    public void setLast7DaysFailures(long last7DaysFailures) {
        this.last7DaysFailures = last7DaysFailures;
    }
    public long getThisMonthFailures() {
        return thisMonthFailures;
    }
    public void setThisMonthFailures(long thisMonthFailures) {
        this.thisMonthFailures = thisMonthFailures;
    }
}