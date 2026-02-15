package com.failureprediction.backendfailureprediction.service.impl;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryResponse;
import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.service.LLMService;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class LLMServiceImpl implements LLMService {

    // ================= ROOT CAUSE =================

    @Override
    @Retry(name = "llmRetry", fallbackMethod = "analyzeRootCauseFallback")
    @TimeLimiter(name = "llmTimeout")
    public RootCauseResponse analyzeRootCause(FailedMetricEntity metric) {

        log.info("Running AI analysis for {}", metric.getServiceName());

        // (Optional) simulate delay for testing
        // simulateDelay();

        RootCauseResponse response = new RootCauseResponse();

        response.setServiceName(metric.getServiceName());
        response.setRootCause("High CPU and Memory usage");
        response.setConfidence("0.85");
        response.setRecommendedFix("Scale service and optimize queries");

        return response;
    }

    // ================= FALLBACK =================

    public RootCauseResponse analyzeRootCauseFallback(
            FailedMetricEntity metric,
            Exception ex) {

        log.error("Fallback triggered for {}", metric.getServiceName(), ex);

        RootCauseResponse response = new RootCauseResponse();

        response.setServiceName(metric.getServiceName());
        response.setRootCause("AI temporarily unavailable");
        response.setConfidence("0.0");
        response.setRecommendedFix("Manual investigation required");

        return response;
    }

    // ================= SUMMARY =================

    @Override
    public CompletableFuture<AIFailureSummaryResponse> generateFailureSummary(
            FailedMetricEntity metric,
            RootCauseResponse rootCause) {

        AIFailureSummaryResponse summary =
                new AIFailureSummaryResponse();

        summary.setServiceName(metric.getServiceName());

        summary.setExecutiveSummary(
                "Service instability detected"
        );

        summary.setRootCause(rootCause.getRootCause());
        summary.setConfidence(rootCause.getConfidence());

        return CompletableFuture.completedFuture(summary);
    }

    // ================= TEST METHOD =================

    private void simulateDelay() {

        try {
            Thread.sleep(6000); // 6 sec (to trigger timeout)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}