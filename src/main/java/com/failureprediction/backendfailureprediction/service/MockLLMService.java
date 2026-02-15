package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryResponse;
import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Profile("test")
public class MockLLMService implements LLMService {

    @Override
    public RootCauseResponse analyzeRootCause(FailedMetricEntity metric) {

        RootCauseResponse response = new RootCauseResponse();

        response.setServiceName(metric.getServiceName());
        response.setRootCause("MOCK: Test root cause");
        response.setConfidence("0.99");
        response.setRecommendedFix("MOCK: No action needed");

        return response;
    }

    @Override
    public CompletableFuture<AIFailureSummaryResponse> generateFailureSummary(
            FailedMetricEntity metric,
            RootCauseResponse rootCause) {

        AIFailureSummaryResponse response =
                new AIFailureSummaryResponse(
                        metric.getServiceName(),
                        "Mock summary for testing: " + rootCause.getRootCause(),
                        List.of(
                                "Test action 1",
                                "Test action 2"
                        ),
                        rootCause.getConfidence(),
                        rootCause.getRootCause()
                );

        return CompletableFuture.completedFuture(response);
    }
}