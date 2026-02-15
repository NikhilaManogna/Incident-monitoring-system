package com.failureprediction.backendfailureprediction.repository;

import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FailedMetricRepository
        extends JpaRepository<FailedMetricEntity, Long>,
        JpaSpecificationExecutor<FailedMetricEntity> {

    long countByServiceName(String serviceName);

    @Query("SELECT f FROM FailedMetricEntity f WHERE f.failedAt >= :since")
    List<FailedMetricEntity> findFailuresSince(
            @Param("since") LocalDateTime since);

    List<FailedMetricEntity> findByRiskScoreGreaterThan(Double score);

    @Query("""
        SELECT f
        FROM FailedMetricEntity f
        WHERE f.serviceName = :service
          AND f.failedAt >= :since
        ORDER BY f.failedAt ASC
    """)
    List<FailedMetricEntity> findTimeline(
            @Param("service") String service,
            @Param("since") LocalDateTime since
    );

    Optional<FailedMetricEntity>
    findTopByServiceNameOrderByFailedAtDesc(String serviceName);

    List<FailedMetricEntity> findByStatus(String status);

    long count();
    long countByResolvedFalse();
    long countByResolvedTrue();
    long countByRiskScoreGreaterThan(double risk);

    @Query("SELECT f.serviceName, COUNT(f) FROM FailedMetricEntity f GROUP BY f.serviceName")
    List<Object[]> countByService();

    @Query("SELECT f.status, COUNT(f) FROM FailedMetricEntity f GROUP BY f.status")
    List<Object[]> countByStatus();

    @Query("SELECT COUNT(f) FROM FailedMetricEntity f")
    Long countAllFailures();

    @Query("SELECT COUNT(f) FROM FailedMetricEntity f WHERE f.riskScore >= 70")
    Long countHighRiskFailures();

    @Query("""
       SELECT f.serviceName, COUNT(f)
       FROM FailedMetricEntity f
       GROUP BY f.serviceName
       ORDER BY COUNT(f) DESC
    """)
    List<Object[]> findTopServices();
    long countByFailedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    Page<FailedMetricEntity> findByStatusAndServiceName(
            String status,
            String serviceName,
            Pageable pageable
    );
    Page<FailedMetricEntity> findByStatus(
            String status,
            Pageable pageable
    );
    Page<FailedMetricEntity> findByServiceName(
            String serviceName,
            Pageable pageable
    );
    @Query(value = """
SELECT f FROM FailedMetricEntity f
WHERE
(:status IS NULL OR f.status = :status)
AND
(:serviceName IS NULL OR f.serviceName = :serviceName)
AND
(:fromDate IS NULL OR f.failedAt >= CAST(:fromDate AS timestamp))
AND
(:toDate IS NULL OR f.failedAt <= CAST(:toDate AS timestamp))
""")
    Page<FailedMetricEntity> searchFailures(
            @Param("status") String status,
            @Param("serviceName") String serviceName,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}