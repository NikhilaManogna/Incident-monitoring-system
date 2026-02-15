package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.RiskTimelineResponse;
import com.failureprediction.backendfailureprediction.service.FailureAnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/failures")
public class FailureAnalyticsController {
    private final FailureAnalyticsService analyticsService;
    public FailureAnalyticsController(
            FailureAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    @GetMapping("/timeline")
    public List<RiskTimelineResponse> getRiskTimeline(
            @RequestParam String service,
            @RequestParam(defaultValue = "24") int hours) {
        return analyticsService.getTimeline(service, hours);
    }
}