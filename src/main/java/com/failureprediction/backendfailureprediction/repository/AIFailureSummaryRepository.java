package com.failureprediction.backendfailureprediction.repository;

import com.failureprediction.backendfailureprediction.entity.AIFailureSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AIFailureSummaryRepository
        extends JpaRepository<AIFailureSummaryEntity, Long> {

    Optional<AIFailureSummaryEntity>
    findTopByServiceNameOrderByGeneratedAtDesc(String serviceName);

    List<AIFailureSummaryEntity>
    findByServiceNameOrderByGeneratedAtDesc(String serviceName);
}