package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;

public interface AiFailureService {

    void analyzeAndSaveFailure(SystemMetricRequest request, double risk);
}