package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.FailureExplanationResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import org.springframework.stereotype.Service;

@Service
public class FailureExplanationService {

    // ✅ EXISTING METHODS (DO NOT TOUCH)
    public String generateExplanation(double cpu, double memory, double disk, int errors) {

        if (cpu > 80 && errors > 10) {
            return "High CPU usage combined with frequent errors indicates possible overload or inefficient processing.";
        }

        if (memory > 75) {
            return "Memory consumption is high, suggesting potential memory leaks or insufficient heap allocation.";
        }

        if (disk > 80) {
            return "Disk usage is critically high which may lead to I/O bottlenecks.";
        }

        return "System metrics are within acceptable limits with no immediate failure indicators.";
    }

    public String recommendAction(double score) {
        if (score >= 70) {
            return "Scale the service immediately and inspect recent deployments.";
        } else if (score >= 40) {
            return "Monitor the service closely and enable alerts.";
        }
        return "No action required. System is healthy.";
    }

    public FailureExplanationResponse explainFailedMetric(FailedMetricEntity metric) {

        String summary = "Service failure detected with elevated risk score";

        String probableCause =
                metric.getCpuUsage() != null && metric.getCpuUsage() > 80
                        ? "CPU utilization exceeded safe operating limits"
                        : "Abnormal system behavior detected";

        String recommendation =
                metric.getRiskScore() != null && metric.getRiskScore() >= 70
                        ? "Immediately scale the service and investigate recent deployments"
                        : "Monitor the service and apply preventive alerts";

        return new FailureExplanationResponse(
                summary,
                probableCause,
                recommendation
        );
    }
}