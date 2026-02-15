package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("!test")
@Service
public class MetricEventConsumer {

    private final SystemMetricService systemMetricService;
    private final AiFailureService aiFailureService;

    public MetricEventConsumer(SystemMetricService systemMetricService,
                               AiFailureService aiFailureService) {
        this.systemMetricService = systemMetricService;
        this.aiFailureService = aiFailureService;
    }

    @KafkaListener(
            topics = "system-metrics",
            groupId = "metrics-group"
    )
    public void consume(SystemMetricRequest request) {

        log.info("Received metric for service: {}", request.getServiceName());

        // Calculate risk
        double risk = calculateRisk(request);

        log.info("Calculated Risk = {}", risk);

        // Save normal metric
        systemMetricService.save(request, risk);

        log.info("Metric saved successfully for {}", request.getServiceName());

        // If high risk → trigger AI analysis (NO EXCEPTION)
        if (risk > 70) {

            log.warn("High risk detected: {}", risk);

            aiFailureService.analyzeAndSaveFailure(
                    request,
                    risk
            );
        }
    }

    private double calculateRisk(SystemMetricRequest metric) {

        double errorRate = metric.getRequestCount() == 0
                ? 0
                : (double) metric.getErrorCount() / metric.getRequestCount();

        return (metric.getCpuUsage() * 0.5)
                + (metric.getMemoryUsage() * 0.3)
                + (errorRate * 20);
    }
}