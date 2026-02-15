package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.failureprediction.backendfailureprediction.repository.IncidentHistoryRepository;
import com.failureprediction.backendfailureprediction.service.FailedMetricService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.failureprediction.backendfailureprediction.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/failures")
public class FailureMetricsController {
    private final FailedMetricService service;
    private final IncidentHistoryRepository historyRepository;
    public FailureMetricsController(FailedMetricService service, IncidentHistoryRepository historyRepository) {
        this.service = service;
        this.historyRepository = historyRepository;
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public List<FailedMetricEntity> getAllFailures() {
        return service.getAllFailures();
    }
    @GetMapping("/service/{serviceName}")
    public List<FailedMetricEntity> getFailuresByService(
            @PathVariable String serviceName) {
        return service.getFailuresByService(serviceName);
    }
    @GetMapping("/count")
    public long totalFailures() {
        return service.getAllFailures().size();
    }
    @GetMapping("/by-service")
    public Map<String, Long> failuresByService() {
        return service.getFailureCountByService();
    }
    @GetMapping("/recent")
    public List<FailedMetricEntity> recentFailures(
            @RequestParam(defaultValue = "24") int hours) {
        return service.getRecentFailures(hours);
    }
    @GetMapping("/high-risk")
    public List<FailedMetricEntity> highRiskFailures(
            @RequestParam(defaultValue = "70") double minScore) {
        return service.getHighRiskFailures(minScore);
    }
    @GetMapping("/failures")
    public ResponseEntity<ApiResponse<Page<FailedMetricEntity>>> getFailures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        Page<FailedMetricEntity> result =
                service.getFailures(
                        page,
                        size,
                        status,
                        serviceName,
                        from,
                        to
                );
        ApiResponse<Page<FailedMetricEntity>> response =
                new ApiResponse<>(
                        true,
                        "Failures fetched successfully",
                        result
                );
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resolve(
            @PathVariable Long id,
            @RequestParam String resolvedBy,
            @RequestParam String comment
    ) {
        service.resolveFailure(id, resolvedBy, comment);
        return ResponseEntity.ok(
                Map.of("message", "Incident resolved successfully")
        );
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<FailedMetricEntity>> getFailureById(
            @PathVariable Long id
    ) {
        FailedMetricEntity failure =
                service.getFailureById(id);
        ApiResponse<FailedMetricEntity> response =
                new ApiResponse<>(
                        true,
                        "Failure fetched successfully",
                        failure
                );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = service.getStats();
        ApiResponse<Map<String, Object>> response =
                new ApiResponse<>(
                        true,
                        "Failure statistics fetched successfully",
                        stats
                );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<FailedMetricEntity>>> searchFailures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        Page<FailedMetricEntity> result =
                service.getFailures(
                        page,
                        size,
                        status,
                        serviceName,
                        from,
                        to
                );
        ApiResponse<Page<FailedMetricEntity>> response =
                new ApiResponse<>(
                        true,
                        "Failures searched successfully",
                        result
                );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ApiResponse<Page<?>>> getHistory(
            @PathVariable Long id,
            Pageable pageable
    ) {
        Page<?> history =
                historyRepository.findByFailureId(id, pageable);
        ApiResponse<Page<?>> response =
                new ApiResponse<>(
                        true,
                        "Incident history fetched successfully",
                        history
                );
        return ResponseEntity.ok(response);
    }
}