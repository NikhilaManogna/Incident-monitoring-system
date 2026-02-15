package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryHistoryResponse;
import com.failureprediction.backendfailureprediction.entity.AIFailureSummaryEntity;
import com.failureprediction.backendfailureprediction.repository.AIFailureSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIFailureSummaryQueryService {

    private final AIFailureSummaryRepository repository;

    public AIFailureSummaryQueryService(AIFailureSummaryRepository repository) {
        this.repository = repository;
    }

    // ================= Latest Summary =================

    public AIFailureSummaryHistoryResponse getLatestSummary(String serviceName) {

        AIFailureSummaryEntity entity =
                repository.findTopByServiceNameOrderByGeneratedAtDesc(serviceName)
                        .orElseThrow(() ->
                                new RuntimeException("No AI summary found for service"));

        return map(entity);
    }

    // ================= History =================

    public List<AIFailureSummaryHistoryResponse> getSummaryHistory(String serviceName) {

        List<AIFailureSummaryEntity> list =
                repository.findByServiceNameOrderByGeneratedAtDesc(serviceName);

        // Prevent 500 error when empty
        if (list == null || list.isEmpty()) {
            return List.of();
        }

        return list.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ================= Mapper =================

    private AIFailureSummaryHistoryResponse map(AIFailureSummaryEntity entity) {

        AIFailureSummaryHistoryResponse response =
                new AIFailureSummaryHistoryResponse();

        response.setId(entity.getId());
        response.setServiceName(entity.getServiceName());
        response.setExecutiveSummary(entity.getExecutiveSummary());
        response.setBusinessImpact(entity.getBusinessImpact());
        response.setRecommendedActions(entity.getRecommendedActions());
        response.setConfidence(entity.getConfidence());
        response.setGeneratedAt(entity.getGeneratedAt());

        return response;
    }
}