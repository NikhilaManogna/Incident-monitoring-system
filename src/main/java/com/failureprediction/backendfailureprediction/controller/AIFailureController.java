package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.*;
import com.failureprediction.backendfailureprediction.entity.*;
import com.failureprediction.backendfailureprediction.repository.*;
import com.failureprediction.backendfailureprediction.service.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/failures")
public class AIFailureController {
    private final FailedMetricRepository failedRepo;
    private final RootCauseAnalysisService rootCauseService;
    private final LLMService llmService;
    private final AIFailureSummaryRepository summaryRepo;
    private final AIFailureSummaryQueryService queryService;
    private final RiskForecastService riskForecastService;
    private final AIRiskForecastRepository forecastRepo;
    public AIFailureController(
            FailedMetricRepository failedRepo,
            RootCauseAnalysisService rootCauseService,
            LLMService llmService,
            AIFailureSummaryRepository summaryRepo,
            AIFailureSummaryQueryService queryService,
            RiskForecastService riskForecastService,
            AIRiskForecastRepository forecastRepo) {
        this.failedRepo = failedRepo;
        this.rootCauseService = rootCauseService;
        this.llmService = llmService;
        this.summaryRepo = summaryRepo;
        this.queryService = queryService;
        this.riskForecastService = riskForecastService;
        this.forecastRepo = forecastRepo;
    }
    @GetMapping("/{serviceName}/ai-summary")
    public Object getAISummary(@PathVariable String serviceName) {
        FailedMetricEntity metric =
                failedRepo
                        .findTopByServiceNameOrderByFailedAtDesc(serviceName)
                        .orElse(null);
        if (metric == null) {
            return new MessageResponse("No failure data available yet");
        }
        AIFailureSummaryResponse response = new AIFailureSummaryResponse();
        response.setServiceName(metric.getServiceName());
        response.setExecutiveSummary(metric.getAiSummary());
        response.setBusinessImpact(metric.getBusinessImpact());
        response.setRecommendedActions(
                List.of(metric.getRecommendedActions().split("\\|"))
        );
        response.setConfidence(metric.getConfidence());
        response.setRootCause(metric.getRootCause());
        return response;
    }
    @GetMapping("/{serviceName}/ai-summary/latest")
    public Object getLatestSummary(@PathVariable String serviceName) {
        try {
            return queryService.getLatestSummary(serviceName);
        } catch (Exception e) {
            return new MessageResponse("No AI summary found yet");
        }
    }
    @GetMapping("/{serviceName}/ai-summary/history")
    public List<AIFailureSummaryHistoryResponse> getSummaryHistory(
            @PathVariable String serviceName) {

        return queryService.getSummaryHistory(serviceName);
    }
    @GetMapping("/{serviceName}/ai-risk-forecast")
    public RiskForecastResponse forecastRisk(
            @PathVariable String serviceName) {
        RiskForecastResponse response =
                riskForecastService.forecastRisk(serviceName);
        AIRiskForecastEntity entity = new AIRiskForecastEntity();
        entity.setServiceName(serviceName);
        entity.setPredictedRisk(response.getPredictedRisk());
        entity.setConfidence(response.getConfidence());
        entity.setReasoning(response.getReasoning());
        entity.setRecommendedPreventiveActions(
                response.getRecommendedPreventiveActions()
        );
        forecastRepo.save(entity);
        return response;
    }
    @GetMapping("/open")
    public List<FailedMetricEntity> getOpenIncidents() {
        return failedRepo.findByStatus("OPEN");
    }
}