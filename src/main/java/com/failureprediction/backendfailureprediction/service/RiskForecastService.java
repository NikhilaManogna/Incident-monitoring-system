package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.RiskForecastResponse;
import com.failureprediction.backendfailureprediction.entity.SystemMetric;
import com.failureprediction.backendfailureprediction.repository.SystemMetricRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiskForecastService {

    private final SystemMetricRepository repository;

    public RiskForecastService(SystemMetricRepository repository) {
        this.repository = repository;
    }

    public RiskForecastResponse forecastRisk(String serviceName) {

        List<SystemMetric> metrics =
                repository.findByServiceNameOrderByTimestampAsc(serviceName);

        RiskForecastResponse response = new RiskForecastResponse();
        response.setServiceName(serviceName);

        // ✅ ENTERPRISE SAFE HANDLING
        if (metrics.size() < 3) {

            response.setPredictedRisk("UNKNOWN");
            response.setConfidence(0.0);

            response.setReasoning(List.of(
                    "Insufficient historical data to forecast risk",
                    "At least 3 metric samples are required for trend analysis"
            ));

            response.setRecommendedPreventiveActions(List.of(
                    "Continue collecting metrics",
                    "Enable continuous monitoring",
                    "Re-evaluate after sufficient data is available"
            ));

            return response;
        }

        double prev = metrics.get(metrics.size() - 3).getFailureScore();
        double mid = metrics.get(metrics.size() - 2).getFailureScore();
        double latest = metrics.get(metrics.size() - 1).getFailureScore();

        List<String> reasoning = new ArrayList<>();
        List<String> actions = new ArrayList<>();

        boolean increasingTrend = latest > mid && mid > prev;

        int highRiskCount = 0;
        if (prev >= 70) highRiskCount++;
        if (mid >= 70) highRiskCount++;
        if (latest >= 70) highRiskCount++;

        String predictedRisk;
        double confidence;

        if (highRiskCount >= 2 && increasingTrend) {
            predictedRisk = "HIGH";
            confidence = 0.82;
            reasoning.add("Failure score has increased consistently");
            reasoning.add("Service entered HIGH risk multiple times");
            reasoning.add("CPU saturation observed in recent metrics");

            actions.add("Scale resources proactively");
            actions.add("Enable circuit breakers");
            actions.add("Introduce rate limiting");
        } else if (latest >= 50) {
            predictedRisk = "MEDIUM";
            confidence = 0.55;
            reasoning.add("Failure score trending upward");

            actions.add("Monitor traffic closely");
            actions.add("Prepare scaling policies");
        } else {
            predictedRisk = "LOW";
            confidence = 0.30;
            reasoning.add("Failure score stable");

            actions.add("No immediate action required");
        }

        response.setPredictedRisk(predictedRisk);
        response.setConfidence(confidence);
        response.setReasoning(reasoning);
        response.setRecommendedPreventiveActions(actions);

        return response;
    }
}