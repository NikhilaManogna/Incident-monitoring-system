package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FailedMetricDlqConsumer {

    private final FailedMetricRepository repository;

    public FailedMetricDlqConsumer(FailedMetricRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "system-metrics-dlq",
            groupId = "metrics-dlq-group"
    )
    public void consumeDlq(SystemMetricRequest metric) {

        log.error("DLQ MESSAGE RECEIVED for {}", metric.getServiceName());

        FailedMetricEntity failed = new FailedMetricEntity();
        failed.setServiceName(metric.getServiceName());
        failed.setCpuUsage(metric.getCpuUsage());
        failed.setMemoryUsage(metric.getMemoryUsage());
        failed.setDiskUsage(metric.getDiskUsage());
        failed.setRequestCount(metric.getRequestCount());
        failed.setErrorCount(metric.getErrorCount());
        failed.setFailureReason("CPU threshold exceeded");

        failed.setRiskScore(calculateRisk(metric));

        repository.save(failed);

        log.info("Failed metric saved with riskScore={}", failed.getRiskScore());
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