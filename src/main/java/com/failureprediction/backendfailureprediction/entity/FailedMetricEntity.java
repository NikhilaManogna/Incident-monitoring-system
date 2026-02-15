package com.failureprediction.backendfailureprediction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "failed_metrics")
public class FailedMetricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serviceName;
    private Float cpuUsage;
    private Float memoryUsage;
    private Float diskUsage;
    private Long requestCount;
    private Long errorCount;
    @Column(length = 500)
    private String failureReason;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime failedAt;
    @Column(name = "risk_score")
    private Double riskScore;
    private Double risk;
    private String rootCause;
    private String confidence;
    private String aiSummary;
    @Column(columnDefinition = "TEXT")
    private String businessImpact;
    @Column(columnDefinition = "TEXT")
    private String recommendedActions;
    @Column(nullable = false)
    private String status="OPEN"; // OPEN / RESOLVED
    @Column(nullable = false)
    private boolean resolved = false;
    @Column(nullable = false)
    private String aiStatus = "PENDING";
    @Column
    private LocalDateTime resolvedAt;
    @Column(name = "resolved_by")
    private String resolvedBy;
    @Column(name = "resolution_comment")
    private String resolutionComment;

    @PrePersist
    public void onCreate() {
        this.failedAt = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public Float getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Float cpuUsage) { this.cpuUsage = cpuUsage; }
    public Float getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(Float memoryUsage) { this.memoryUsage = memoryUsage; }
    public Float getDiskUsage() { return diskUsage; }
    public void setDiskUsage(Float diskUsage) { this.diskUsage = diskUsage; }
    public Long getRequestCount() { return requestCount; }
    public void setRequestCount(Long requestCount) { this.requestCount = requestCount; }
    public Long getErrorCount() { return errorCount; }
    public void setErrorCount(Long errorCount) { this.errorCount = errorCount; }
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    public LocalDateTime getFailedAt() { return failedAt; }
    public void setFailedAt(LocalDateTime failedAt) {this.failedAt=failedAt; }
    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) { this.riskScore = riskScore; }
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause=rootCause; }
    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) { this.confidence=confidence; }
    public String getAiSummary() { return aiSummary; }
    public void setAiSummary(String executiveSummary) { this.aiSummary = executiveSummary; }
    public String getBusinessImpact() { return businessImpact; }
    public void setBusinessImpact(String businessImpact) { this.businessImpact=businessImpact; }
    public String getRecommendedActions() { return recommendedActions; }
    public void setRecommendedActions(String recommendedActions) { this.recommendedActions=recommendedActions; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status=status; }
    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
    public Double getRisk() { return risk; }
    public void setRisk(double risk) { this.risk=risk; }
    public String getAiStatus() { return aiStatus; }
    public void setAiStatus(String aiStatus) { this.aiStatus = aiStatus; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
    public String getResolvedBy() {return resolvedBy; }
    public void setResolvedBy(String resolvedBy) { this.resolvedBy=resolvedBy; }
    public String getResolutionComment() { return resolutionComment; }
    public void setResolutionComment(String resolutionComment) { this.resolutionComment=resolutionComment; }

    @Transient
    public String getRiskLevel() {
        if (riskScore == null) return "UNKNOWN";
        if (riskScore < 30) return "LOW";
        if (riskScore < 60) return "MEDIUM";
        if (riskScore < 80) return "HIGH";
        return "CRITICAL";
    }
}