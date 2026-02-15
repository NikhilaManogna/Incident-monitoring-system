package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.AIFailureSummaryResponse;
import com.failureprediction.backendfailureprediction.dto.RootCauseResponse;
import com.failureprediction.backendfailureprediction.entity.FailedMetricEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Primary
public class OllamaLLMService implements LLMService {

    @Value("${llm.ollama.url}")
    private String ollamaUrl;

    @Value("${llm.ollama.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();


    // ======================================================
    // MAIN AI METHOD (RETRY + TIMEOUT + ASYNC)
    // ======================================================

    @Override
    @Retry(name = "ollamaRetry", fallbackMethod = "ollamaFallback")
    @TimeLimiter(name = "ollamaTimeout", fallbackMethod = "ollamaFallback")
    public CompletableFuture<AIFailureSummaryResponse> generateFailureSummary(
            FailedMetricEntity metric,
            RootCauseResponse rootCause) {

        return CompletableFuture.completedFuture(callOllama(metric, rootCause));
    }


    // ======================================================
    // REAL OLLAMA CALL (SYNC)
    // ======================================================

    private AIFailureSummaryResponse callOllama(
            FailedMetricEntity metric,
            RootCauseResponse rootCause) {

        try {

            log.info("Calling Ollama AI for {}", metric.getServiceName());

            String prompt = """
You are an AI reliability engineer.

Return ONLY valid JSON.

{
  "executiveSummary": "",
  "businessImpact": "",
  "recommendedActions": ["",""],
  "confidence": "0.0-1.0"
}

Service: %s
CPU: %.2f
Memory: %.2f
Disk: %.2f
Errors: %d
Requests: %d
Risk: %.2f
Root Cause: %s
"""
                    .formatted(
                            metric.getServiceName(),
                            metric.getCpuUsage(),
                            metric.getMemoryUsage(),
                            metric.getDiskUsage(),
                            metric.getErrorCount(),
                            metric.getRequestCount(),
                            metric.getRiskScore(),
                            rootCause.getRootCause()
                    );

            Map<String, Object> body = Map.of(
                    "model", model,
                    "prompt", prompt,
                    "stream", false
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            ollamaUrl,
                            request,
                            Map.class
                    );

            if (response.getBody() == null) {
                throw new RuntimeException("Empty Ollama response");
            }

            String raw =
                    response.getBody().get("response").toString();

            log.info("RAW AI RESPONSE (RAW): {}", raw);

// Clean markdown
            String cleaned = cleanJson(raw);

            log.info("RAW AI RESPONSE (CLEANED): {}", cleaned);

            return mapper.readValue(
                    cleaned,
                    AIFailureSummaryResponse.class
            );

        } catch (Exception e) {

            log.error("Ollama failed", e);

            // IMPORTANT: Throw for retry
            throw new RuntimeException(e);
        }
    }


    // ======================================================
    // ROOT CAUSE
    // ======================================================

    @Override
    public RootCauseResponse analyzeRootCause(FailedMetricEntity metric) {

        RootCauseResponse response = new RootCauseResponse();

        response.setServiceName(metric.getServiceName());
        response.setRootCause("High resource usage detected");
        response.setConfidence("0.80");
        response.setRecommendedFix("Check CPU, memory and scale service");

        return response;
    }


    // ======================================================
    // FALLBACK
    // ======================================================

    public CompletableFuture<AIFailureSummaryResponse> ollamaFallback(
            FailedMetricEntity metric,
            RootCauseResponse rootCause,
            Throwable ex) {

        log.error("Retry exhausted. Using fallback for {}. Reason: {}",
                metric.getServiceName(),
                ex.getMessage());

        AIFailureSummaryResponse response =
                new AIFailureSummaryResponse();

        response.setExecutiveSummary("AI unavailable (fallback)");
        response.setBusinessImpact("Manual investigation required");
        response.setConfidence("0.0");
        response.setRecommendedActions(
                List.of("Check system manually", "Restart Ollama")
        );

        return CompletableFuture.completedFuture(response);
    }
    private String cleanJson(String raw) {

        if (raw == null) return null;

        // Remove ```json and ```
        String cleaned = raw
                .replaceAll("(?i)```json", "")
                .replaceAll("```", "");

        // Remove single-line comments (// ...)
        cleaned = cleaned.replaceAll("//.*", "");

        // Trim spaces
        cleaned = cleaned.trim();

        return cleaned;
    }
}