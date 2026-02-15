package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.*;
import com.failureprediction.backendfailureprediction.entity.SystemMetric;
import com.failureprediction.backendfailureprediction.repository.SystemMetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemMetricServiceImpl implements SystemMetricService {

    private final SystemMetricRepository repository;
    private final AiFailureService aiFailureService;

    public SystemMetricServiceImpl(SystemMetricRepository repository, AiFailureService aiFailureService) {
        this.repository = repository;
        this.aiFailureService=aiFailureService;
    }

    // ================= SAVE METHODS =================

    @Override
    public SystemMetric save(SystemMetricRequest request) {

        double risk = calculateRisk(request);

        // Save metric
        SystemMetric metric = saveAndReturn(request, risk);

        // Call AI failure analysis
        if (risk >= 50) { // only if risky
            aiFailureService.analyzeAndSaveFailure(request, risk);
        }

        return metric;
    }

    @Override
    public void save(SystemMetricRequest request, double risk) {

        saveAndReturn(request, risk);
    }

    private SystemMetric saveAndReturn(SystemMetricRequest request, double risk) {

        SystemMetric metric = new SystemMetric();

        metric.setServiceName(request.getServiceName());
        metric.setCpuUsage(request.getCpuUsage());
        metric.setMemoryUsage(request.getMemoryUsage());
        metric.setRequestCount(request.getRequestCount());
        metric.setErrorCount(request.getErrorCount());
        metric.setFailureScore(risk); // <-- match repository field

        return repository.save(metric);
    }

    // ================= RISK CALC =================

    private double calculateRisk(SystemMetricRequest metric) {

        double errorRate = metric.getRequestCount() == 0
                ? 0
                : (double) metric.getErrorCount() / metric.getRequestCount();

        return (metric.getCpuUsage() * 0.5)
                + (metric.getMemoryUsage() * 0.3)
                + (errorRate * 20);
    }

    // ================= READ METHODS =================

    @Override
    public SystemMetric getLatestMetric(String serviceName) {

        return repository
                .findTopByServiceNameOrderByTimestampDesc(serviceName)
                .orElse(null);
    }

    @Override
    public List<SystemMetric> getRiskyServices(double threshold) {

        return repository.findByFailureScoreGreaterThan(threshold);
    }

    @Override
    public List<SystemMetric> getServiceHistory(String serviceName) {

        return repository.findByServiceNameOrderByTimestampAsc(serviceName);
    }

    // ================= AI / ANALYTICS (TEMP SIMPLE) =================

    @Override
    public FailurePredictionResponse predictFailure(String serviceName) {

        FailurePredictionResponse res = new FailurePredictionResponse();

        res.setServiceName(serviceName);
        res.setFailureScore(25);
        res.setRiskLevel("LOW");
        res.setAction("MONITOR");
        res.setExplanation("System is currently stable");
        res.setRecommendedAction("No immediate action required");

        return res;
    }

    @Override
    public FailureScoreExplanationResponse explainFailureScore(String serviceName) {

        FailureScoreExplanationResponse res =
                new FailureScoreExplanationResponse();

        res.setServiceName(serviceName);
        res.setCpuContribution(30);
        res.setMemoryContribution(25);
        res.setDiskContribution(20);
        res.setErrorContribution(15);
        res.setFinalScore(90);

        return res;
    }

    @Override
    public RiskTrendResponse analyzeTrend(String serviceName) {

        RiskTrendResponse res = new RiskTrendResponse();

        res.setTrend("STABLE");

        return res;
    }

    @Override
    public List<RiskTimelineResponse> getRiskTimeline(String serviceName) {

        return List.of();
    }

    @Override
    public AlertResponse checkAlerts(String serviceName) {

        AlertResponse res = new AlertResponse();

        res.setAlert(false);
        res.setMessage("No alert");

        return res;
    }
}