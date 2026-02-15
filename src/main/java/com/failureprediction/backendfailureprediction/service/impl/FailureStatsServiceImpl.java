package com.failureprediction.backendfailureprediction.service.impl;

import com.failureprediction.backendfailureprediction.dto.FailureStats;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import com.failureprediction.backendfailureprediction.service.FailureStatsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FailureStatsServiceImpl implements FailureStatsService {

    private final FailedMetricRepository repository;

    public FailureStatsServiceImpl(FailedMetricRepository repository) {
        this.repository = repository;
    }

    @Override
    public FailureStats getFailureStats() {

        long total = repository.count();
        long open = repository.countByResolvedFalse();
        long resolved = repository.countByResolvedTrue();
        long highRisk = repository.countByRiskScoreGreaterThan(70);

        List<Object[]> rows = repository.countByService();

        Map<String, Long> serviceMap = new HashMap<>();

        for (Object[] row : rows) {
            String service = (String) row[0];
            Long count = (Long) row[1];

            serviceMap.put(service, count);
        }

        return new FailureStats(
                total,
                open,
                resolved,
                highRisk,
                serviceMap
        );
    }
}