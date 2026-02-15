package com.failureprediction.backendfailureprediction.service.impl;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryResponse;
import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import com.failureprediction.backendfailureprediction.service.AiFailureService;
import com.failureprediction.backendfailureprediction.service.LLMService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiFailureServiceImpl implements AiFailureService {
    private final FailedMetricRepository failedRepo;
    private final LLMService llmService;
    public AiFailureServiceImpl(FailedMetricRepository failedRepo,
                                LLMService llmService) {
        this.failedRepo = failedRepo;
        this.llmService = llmService;
    }

    @Override
    public void analyzeAndSaveFailure(SystemMetricRequest request, double risk) {
        FailedMetricEntity entity = new FailedMetricEntity();
        entity.setServiceName(request.getServiceName());
        entity.setCpuUsage(request.getCpuUsage());
        entity.setMemoryUsage(request.getMemoryUsage());
        entity.setDiskUsage(request.getDiskUsage());
        entity.setRequestCount(request.getRequestCount());
        entity.setErrorCount(request.getErrorCount());
        entity.setRiskScore(risk);
        entity.setResolved(false);
        entity.setStatus("OPEN");
        entity.setFailureReason("High system risk detected");
        try {
            log.info("Running AI analysis for {}", request.getServiceName());
            RootCauseResponse rootCause =
                    llmService.analyzeRootCause(entity);
            AIFailureSummaryResponse aiResponse =
                    llmService
                            .generateFailureSummary(entity, rootCause)
                            .join();
            entity.setAiSummary(aiResponse.getExecutiveSummary());
            entity.setBusinessImpact(aiResponse.getBusinessImpact());
            entity.setConfidence(aiResponse.getConfidence());
            entity.setAiStatus("SUCCESS");
        } catch (Exception e) {
            log.error("AI Analysis Failed for {}", request.getServiceName(), e);
            entity.setAiSummary("AI analysis failed. Please check system resources.");
            entity.setAiStatus("FAILED");
        }
        failedRepo.save(entity);
        log.info("Failure record saved for {}", request.getServiceName());
    }
}