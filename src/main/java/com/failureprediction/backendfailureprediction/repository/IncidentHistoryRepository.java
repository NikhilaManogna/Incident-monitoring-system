package com. failureprediction.backendfailureprediction.repository;

import com. failureprediction. backendfailureprediction. entity. IncidentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org. springframework.data. jpa.repository. JpaRepository;

import java.util.List;

public interface IncidentHistoryRepository
        extends JpaRepository<IncidentHistory, Long> {

    Page<IncidentHistory> findByFailureId(Long failureId, Pageable pageable);
}