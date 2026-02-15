package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;
import com.failureprediction.backendfailureprediction.service.MetricEventProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    private final MetricEventProducer metricEventProducer;
    public MetricsController(MetricEventProducer metricEventProducer) {
        this.metricEventProducer = metricEventProducer;
    }
    @PostMapping("/ingest")
    public ResponseEntity<?> ingestMetric(
            @Valid @RequestBody SystemMetricRequest request) {
        request.setTimestamp(LocalDateTime.now());
        metricEventProducer.publish(request);
        return ResponseEntity.ok(
                Map.of("status", "Metric ingested successfully")
        );
    }
}