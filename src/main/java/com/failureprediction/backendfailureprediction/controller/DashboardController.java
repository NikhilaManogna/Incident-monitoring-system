package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.DashboardResponse;
import com.failureprediction.backendfailureprediction.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "System analytics and monitoring APIs")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) {
        this.service = service;
    }
    @GetMapping("/summary")
    @Operation(summary = "Get system dashboard statistics")
    public DashboardResponse getDashboard() {
        return service.getDashboardData();
    }
}