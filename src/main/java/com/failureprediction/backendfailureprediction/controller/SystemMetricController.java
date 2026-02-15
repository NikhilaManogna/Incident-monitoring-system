package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.*;
import com.failureprediction.backendfailureprediction.entity.SystemMetric;
import com.failureprediction.backendfailureprediction.service.SystemMetricService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@Profile("!test")
@Tag(
        name = "System Metrics & Failure Prediction",
        description = "APIs for ingesting metrics, predicting failures, trends, alerts and RCA"
)
@RestController
@RequestMapping("/api/metrics")
public class SystemMetricController {
    private final SystemMetricService service;
    public SystemMetricController(SystemMetricService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<SystemMetric> saveMetric(
            @Valid @RequestBody SystemMetricRequest request) {
        return ResponseEntity.ok(service.save(request));
    }
    @GetMapping("/latest/{serviceName}")
    public ResponseEntity<SystemMetric> getLatestMetric(
            @PathVariable String serviceName) {
        return ResponseEntity.ok(service.getLatestMetric(serviceName));
    }
    @GetMapping("/risky")
    public ResponseEntity<List<SystemMetric>> getRiskyServices(
            @RequestParam(defaultValue = "70") double threshold) {
        return ResponseEntity.ok(service.getRiskyServices(threshold));
    }
    @GetMapping("/predict/{serviceName}")
    public ResponseEntity<FailurePredictionResponse> predictFailure(
            @PathVariable String serviceName) {
        return ResponseEntity.ok(service.predictFailure(serviceName));
    }
    @GetMapping("/explain/{serviceName}")
    public FailureScoreExplanationResponse explain(
            @PathVariable String serviceName) {
        return service.explainFailureScore(serviceName);
    }
    @GetMapping("/history/{serviceName}")
    public ResponseEntity<List<SystemMetric>> getHistory(
            @PathVariable String serviceName) {
        return ResponseEntity.ok(service.getServiceHistory(serviceName));
    }
    @GetMapping("/trend/{serviceName}")
    public RiskTrendResponse getTrend(
            @PathVariable String serviceName) {
        return service.analyzeTrend(serviceName);
    }
    @GetMapping("/risk-timeline/{serviceName}")
    public ResponseEntity<List<RiskTimelineResponse>> getRiskTimeline(
            @PathVariable String serviceName) {
        return ResponseEntity.ok(service.getRiskTimeline(serviceName));
    }
    @GetMapping("/alerts/{serviceName}")
    public AlertResponse getAlerts(
            @PathVariable String serviceName) {
        return service.checkAlerts(serviceName);
    }
}