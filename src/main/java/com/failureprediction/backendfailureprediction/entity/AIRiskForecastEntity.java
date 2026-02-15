package com.failureprediction.backendfailureprediction.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "ai_risk_forecasts")
public class AIRiskForecastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private String predictedRisk;
    private double confidence;
    @ElementCollection
    @CollectionTable(
            name = "ai_risk_forecast_reasoning",
            joinColumns = @JoinColumn(name = "forecast_id")
    )
    @Column(name = "reason")
    private List<String> reasoning;
    @ElementCollection
    @CollectionTable(
            name = "ai_risk_forecast_actions",
            joinColumns = @JoinColumn(name = "forecast_id")
    )
    @Column(name = "action")
    private List<String> recommendedPreventiveActions;
    private Instant generatedAt = Instant.now();

    public Long getId() {
        return id;
    }
    public String getServiceName() {
        return serviceName;
    }
    public String getPredictedRisk() {
        return predictedRisk;
    }
    public double getConfidence() {
        return confidence;
    }
    public List<String> getReasoning() {
        return reasoning;
    }
    public List<String> getRecommendedPreventiveActions() {
        return recommendedPreventiveActions;
    }
    public Instant getGeneratedAt() {
        return generatedAt;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public void setPredictedRisk(String predictedRisk) {
        this.predictedRisk = predictedRisk;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    public void setReasoning(List<String> reasoning) {
        this.reasoning = reasoning;
    }
    public void setRecommendedPreventiveActions(List<String> recommendedPreventiveActions) {
        this.recommendedPreventiveActions = recommendedPreventiveActions;
    }
    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }
}