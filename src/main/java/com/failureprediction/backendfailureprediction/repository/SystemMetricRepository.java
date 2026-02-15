package com.failureprediction.backendfailureprediction.repository;

import com.failureprediction.backendfailureprediction.entity.SystemMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemMetricRepository
        extends JpaRepository<SystemMetric, Long> {

    Optional<SystemMetric> findTopByServiceNameOrderByTimestampDesc(String serviceName);
    List<SystemMetric> findByFailureScoreGreaterThan(double threshold);
    List<SystemMetric> findByServiceNameOrderByTimestampAsc(String serviceName);
    List<SystemMetric> findByServiceNameOrderByTimestampDesc(String serviceName);
}