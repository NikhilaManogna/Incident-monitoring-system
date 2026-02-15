package com.failureprediction.backendfailureprediction.service.impl;

import com.failureprediction.backendfailureprediction.dto.DashboardResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import com.failureprediction.backendfailureprediction.service.DashboardService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final FailedMetricRepository repo;

    public DashboardServiceImpl(FailedMetricRepository repo) {
        this.repo = repo;
    }

    @Override
    public DashboardResponse getDashboardData() {

        DashboardResponse response = new DashboardResponse();

        List<FailedMetricEntity> all = repo.findAll();

        // ============================
// Time based stats
// ============================

        LocalDateTime now = LocalDateTime.now();

// Today
        LocalDateTime todayStart =
                LocalDate.now().atStartOfDay();

        long todayCount =
                repo.countByFailedAtBetween(
                        todayStart,
                        now
                );

// Last 7 days
        LocalDateTime last7Days =
                now.minusDays(7);

        long last7DaysCount =
                repo.countByFailedAtBetween(
                        last7Days,
                        now
                );

// This month
        LocalDateTime monthStart =
                LocalDate.now()
                        .withDayOfMonth(1)
                        .atStartOfDay();

        long monthCount =
                repo.countByFailedAtBetween(
                        monthStart,
                        now
                );

// Set to response
        response.setTodayFailures(todayCount);
        response.setLast7DaysFailures(last7DaysCount);
        response.setThisMonthFailures(monthCount);
        response.setTotalFailures(all.size());

        response.setOpenFailures(
                all.stream().filter(f -> "OPEN".equals(f.getStatus())).count()
        );

        response.setResolvedFailures(
                all.stream().filter(f -> "RESOLVED".equals(f.getStatus())).count()
        );

        // Service wise
        Map<String, Long> serviceMap = new HashMap<>();

        for (Object[] obj : repo.countByService()) {
            serviceMap.put(
                    obj[0].toString(),
                    (Long) obj[1]
            );
        }

        response.setServiceWise(serviceMap);

        // Risk wise
        Map<String, Long> riskMap = new HashMap<>();

        for (FailedMetricEntity f : all) {

            String level = f.getRiskLevel();

            riskMap.put(
                    level,
                    riskMap.getOrDefault(level, 0L) + 1
            );
        }

        response.setRiskWise(riskMap);

        return response;
    }
}