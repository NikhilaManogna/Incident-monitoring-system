package com.failureprediction.backendfailureprediction.dto;

public class FailureScoreExplanationResponse {
    private String serviceName;
    private double cpuContribution;
    private double memoryContribution;
    private double diskContribution;
    private double errorContribution;
    private double finalScore;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public double getCpuContribution() {
        return cpuContribution;
    }
    public void setCpuContribution(double cpuContribution) {
        this.cpuContribution = cpuContribution;
    }
    public double getMemoryContribution() {
        return memoryContribution;
    }
    public void setMemoryContribution(double memoryContribution) {
        this.memoryContribution = memoryContribution;
    }
    public double getDiskContribution() {
        return diskContribution;
    }
    public void setDiskContribution(double diskContribution) {
        this.diskContribution = diskContribution;
    }
    public double getErrorContribution() {
        return errorContribution;
    }
    public void setErrorContribution(double errorContribution) {
        this.errorContribution = errorContribution;
    }
    public double getFinalScore() {
        return finalScore;
    }
    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }
}