package com.failureprediction.backendfailureprediction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {
    private Long totalFailures;
    private Long highRiskFailures;
    private Long last7DaysFailures;
    private Map<String, Long> topServices;
    private String aiStatus;
}