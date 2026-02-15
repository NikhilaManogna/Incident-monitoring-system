package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RootCauseAnalysisService {

    private final FailedMetricRepository repository;

    public RootCauseAnalysisService(FailedMetricRepository repository) {
        this.repository = repository;
    }

    public RootCauseResponse analyze(String serviceName) {

        FailedMetricEntity latest =
                repository.findTopByServiceNameOrderByFailedAtDesc(serviceName)
                        .orElseThrow(() ->
                                new RuntimeException("No failed metrics found for service"));

        List<String> observations = new ArrayList<>();

        if (latest.getCpuUsage() != null && latest.getCpuUsage() > 90) {
            observations.add("CPU usage exceeded 90%");
        }

        if (latest.getMemoryUsage() != null && latest.getMemoryUsage() > 85) {
            observations.add("Memory usage is critically high");
        }

        if (latest.getRequestCount() != null && latest.getRequestCount() > 0) {
            double errorRate =
                    ((double) latest.getErrorCount() / latest.getRequestCount()) * 100;

            if (errorRate > 2) {
                observations.add("Error rate crossed 2%");
            }
        }

        String rootCause;
        String confidence;

        if (observations.size() >= 2) {
            rootCause = "Resource saturation combined with elevated error rates";
            confidence = "HIGH";
        } else if (observations.size() == 1) {
            rootCause = observations.get(0);
            confidence = "MEDIUM";
        } else {
            rootCause = "Transient load spike";
            confidence = "LOW";
        }

        RootCauseResponse response = new RootCauseResponse();
        response.setServiceName(serviceName);
        response.setRootCause(rootCause);
        response.setConfidence(confidence);
        response.setObservations(observations);
        response.setRecommendedFix(
                confidence.equals("HIGH")
                        ? "Scale services and inspect recent changes"
                        : "Monitor metrics closely"
        );

        return response;
    }
}