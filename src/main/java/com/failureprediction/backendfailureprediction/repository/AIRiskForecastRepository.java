package com.failureprediction.backendfailureprediction.repository;

import com.failureprediction.backendfailureprediction.entity.AIRiskForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AIRiskForecastRepository
        extends JpaRepository<AIRiskForecastEntity, Long> {

    Optional<AIRiskForecastEntity>
    findTopByServiceNameOrderByGeneratedAtDesc(String serviceName);

    List<AIRiskForecastEntity>
    findByServiceNameOrderByGeneratedAtDesc(String serviceName);
}