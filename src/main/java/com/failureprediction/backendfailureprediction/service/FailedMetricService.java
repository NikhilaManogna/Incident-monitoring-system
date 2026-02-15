package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.exception.ResourceNotFoundException;
import com.failureprediction.backendfailureprediction.repository.FailedMetricRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.failureprediction.backendfailureprediction.entity.IncidentHistory;
import com.failureprediction.backendfailureprediction.repository.IncidentHistoryRepository;

@Service
public class FailedMetricService {

    private final FailedMetricRepository repository;
    private final IncidentHistoryRepository historyRepository;

    public FailedMetricService(FailedMetricRepository repository, IncidentHistoryRepository historyRepository) {
        this.repository = repository;
        this.historyRepository = historyRepository;
    }

    // ================= BASIC =================

    public List<FailedMetricEntity> getAllFailures() {
        return repository.findAll();
    }

    public List<FailedMetricEntity> getFailuresByService(String serviceName) {
        return repository.findAll()
                .stream()
                .filter(f -> f.getServiceName().equalsIgnoreCase(serviceName))
                .toList();
    }

    // ================= STATS =================

    public Map<String, Long> getFailureCountByService() {

        List<FailedMetricEntity> all = repository.findAll();

        Map<String, Long> result = new HashMap<>();

        for (FailedMetricEntity f : all) {
            result.put(
                    f.getServiceName(),
                    result.getOrDefault(f.getServiceName(), 0L) + 1
            );
        }

        return result;
    }

    public List<FailedMetricEntity> getRecentFailures(int hours) {

        LocalDateTime since = LocalDateTime.now().minusHours(hours);

        return repository.findFailuresSince(since);
    }

    public List<FailedMetricEntity> getHighRiskFailures(double minScore) {
        return repository.findByRiskScoreGreaterThan(minScore);
    }

    public Page<FailedMetricEntity> getFailures(
            int page,
            int size,
            String status,
            String serviceName,
            LocalDateTime fromDate,
            LocalDateTime toDate
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "failedAt")
        );

        // Normalize
        if (status != null) {
            status = status.toUpperCase();
        }

        if (serviceName != null) {
            serviceName = serviceName.trim();
        }

        // Get all with pagination first
        Page<FailedMetricEntity> all =
                repository.findAll(pageable);

        // Filter in Java
        String finalStatus = status;
        String finalServiceName = serviceName;
        List<FailedMetricEntity> filtered =
                all.getContent()
                        .stream()
                        .filter(f ->
                                finalStatus == null ||
                                        f.getStatus().equalsIgnoreCase(finalStatus)
                        )
                        .filter(f ->
                                finalServiceName == null ||
                                        f.getServiceName().equalsIgnoreCase(finalServiceName)
                        )
                        .filter(f ->
                                fromDate == null ||
                                        !f.getFailedAt().isBefore(fromDate)
                        )
                        .filter(f ->
                                toDate == null ||
                                        !f.getFailedAt().isAfter(toDate)
                        )
                        .toList();

        return new PageImpl<>(
                filtered,
                pageable,
                all.getTotalElements()
        );
    }
    public Map<String, Object> getStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("total", repository.count());
        stats.put("open", repository.countByResolvedFalse());
        stats.put("resolved", repository.countByResolvedTrue());
        stats.put("highRisk", repository.countByRiskScoreGreaterThan(70));
        stats.put("topServices", repository.findTopServices());

        return stats;
    }
    public void resolveFailure(Long id, String resolvedBy, String comment) {

        FailedMetricEntity failure = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Failure not found with id: " + id));

        // Update incident
        failure.setStatus("RESOLVED");
        failure.setResolved(true);
        failure.setResolvedAt(LocalDateTime.now());
        failure.setResolvedBy(resolvedBy);
        failure.setResolutionComment(comment);

        repository.save(failure);

        // Save history
        IncidentHistory history = new IncidentHistory();

        history.setFailureId(id);
        history.setAction("RESOLVED");
        history.setPerformedBy(resolvedBy);
        history.setComment(comment);

        historyRepository.save(history);
    }
    public FailedMetricEntity getFailureById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Failure not found with id: " + id)
                );
    }
}