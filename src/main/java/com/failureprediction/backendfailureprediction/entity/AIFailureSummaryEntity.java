package com.failureprediction.backendfailureprediction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ai_failure_summaries") // <<< VERY IMPORTANT
public class AIFailureSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    @Column(length = 2000)
    private String executiveSummary;
    @Column(length = 2000)
    private String businessImpact;
    @ElementCollection
    @CollectionTable(
            name = "ai_failure_summary_actions",
            joinColumns = @JoinColumn(name = "summary_id")
    )
    @Column(name = "action")
    private List<String> recommendedActions;
    private String confidence;
    private LocalDateTime generatedAt;
    @PrePersist
    public void onCreate() {
        this.generatedAt = LocalDateTime.now();
    }
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