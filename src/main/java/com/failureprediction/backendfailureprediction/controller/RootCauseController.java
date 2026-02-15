package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.service.RootCauseAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/failures")
public class RootCauseController {
    private final RootCauseAnalysisService service;
    public RootCauseController(RootCauseAnalysisService service) {
        this.service = service;
    }
    @GetMapping("/{serviceName}/root-cause")
    public RootCauseResponse analyze(@PathVariable String serviceName) {
        return service.analyze(serviceName);
    }
}