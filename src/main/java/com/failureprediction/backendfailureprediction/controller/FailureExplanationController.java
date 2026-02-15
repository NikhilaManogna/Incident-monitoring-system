package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.FailureExplanationResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import com.failureprediction.backendfailureprediction.service.FailureExplanationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/failures")
public class FailureExplanationController {
    private final FailedMetricRepository repository;
    private final FailureExplanationService explanationService;
    public FailureExplanationController(
            FailedMetricRepository repository,
            FailureExplanationService explanationService) {
        this.repository = repository;
        this.explanationService = explanationService;
    }
    @GetMapping("/{id}/explanation")
    public FailureExplanationResponse explainFailure(@PathVariable Long id) {
        FailedMetricEntity metric = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failure not found"));
        return explanationService.explainFailedMetric(metric);
    }
}