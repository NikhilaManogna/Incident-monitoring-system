package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryResponse;
import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;

import java.util.concurrent.CompletableFuture;

public interface LLMService {

    RootCauseResponse analyzeRootCause(FailedMetricEntity metric);

    CompletableFuture<AIFailureSummaryResponse> generateFailureSummary(
            FailedMetricEntity metric,
            RootCauseResponse rootCause
    );
}