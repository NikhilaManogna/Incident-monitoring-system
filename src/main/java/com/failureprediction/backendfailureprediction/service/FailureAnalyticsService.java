package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.RiskTimelineResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FailureAnalyticsService {

    private final FailedMetricRepository repository;

    public FailureAnalyticsService(FailedMetricRepository repository) {
        this.repository = repository;
    }

    public List<RiskTimelineResponse> getTimeline(
            String serviceName,
            int hours) {

        LocalDateTime since = LocalDateTime.now().minusHours(hours);

        return repository.findTimeline(serviceName, since)
                .stream()
                .map(this::mapToTimelineResponse)
                .collect(Collectors.toList());
    }

    private RiskTimelineResponse mapToTimelineResponse(
            FailedMetricEntity entity) {

        RiskTimelineResponse response = new RiskTimelineResponse();
        response.setTimestamp(entity.getFailedAt());
        response.setFailureScore(entity.getRiskScore());
        response.setRiskLevel(entity.getRiskLevel());

        return response;
    }
}