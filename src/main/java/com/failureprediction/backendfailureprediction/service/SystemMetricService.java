package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.*;
import com.failureprediction.backendfailureprediction.entity.SystemMetric;

import java.util.List;

public interface SystemMetricService {

    // For REST API
    SystemMetric save(SystemMetricRequest request);

    // For Kafka
    void save(SystemMetricRequest request, double risk);

    SystemMetric getLatestMetric(String serviceName);

    List<SystemMetric> getRiskyServices(double threshold);

    FailurePredictionResponse predictFailure(String serviceName);

    FailureScoreExplanationResponse explainFailureScore(String serviceName);

    List<SystemMetric> getServiceHistory(String serviceName);

    RiskTrendResponse analyzeTrend(String serviceName);

    List<RiskTimelineResponse> getRiskTimeline(String serviceName);

    AlertResponse checkAlerts(String serviceName);
}