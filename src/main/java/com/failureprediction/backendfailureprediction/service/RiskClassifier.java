package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.model.RiskLevel;
import org.springframework.stereotype.Component;

@Component
public class RiskClassifier {

    public RiskLevel classify(double score) {

        if (score < 30) return RiskLevel.LOW;
        if (score < 60) return RiskLevel.MEDIUM;
        if (score < 80) return RiskLevel.HIGH;

        return RiskLevel.CRITICAL;
    }
}