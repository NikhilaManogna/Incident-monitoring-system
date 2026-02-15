package com.failureprediction.backendfailureprediction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
public class SystemMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serviceName;
    private double cpuUsage;
    private double memoryUsage;
    private long requestCount;
    private long errorCount;
    private float diskUsage;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "failure_score")
    private Double failureScore;
    private Double risk;

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }
    public double getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(double memoryUsage) { this.memoryUsage = memoryUsage; }
    public long getRequestCount() { return requestCount; }
    public void setRequestCount(long requestCount) { this.requestCount = requestCount; }
    public long getErrorCount() { return errorCount; }
    public void setErrorCount(long errorCount) { this.errorCount = errorCount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Double getFailureScore() {
        return failureScore;
    }
    public void setFailureScore(Double failureScore) {
        this.failureScore = failureScore;
    }
    public float getDiskUsage(){ return diskUsage;}
    public void setDiskUsage(float diskUsage) { this.diskUsage=diskUsage; }
    public Double getRiskScore() { return risk; }
    public void setRiskScore(double risk) { this.risk=risk; }
}