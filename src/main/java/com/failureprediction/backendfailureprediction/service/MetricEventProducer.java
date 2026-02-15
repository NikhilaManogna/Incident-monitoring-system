package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.SystemMetricRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricEventProducer {

    private static final String TOPIC = "system-metrics";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MetricEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(SystemMetricRequest request) {
        log.info("Publishing metric to Kafka for service: {}", request.getServiceName());
        kafkaTemplate.send(TOPIC, request.getServiceName(), request);
    }
}