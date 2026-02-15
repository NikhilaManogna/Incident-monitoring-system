package com.failureprediction.backendfailureprediction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "risk")
public class RiskThresholdConfig {
    private double lowThreshold;
    private double mediumThreshold;
    public double getLowThreshold() {
        return lowThreshold;
    }
    public void setLowThreshold(double lowThreshold) {
        this.lowThreshold = lowThreshold;
    }
    public double getMediumThreshold() {
        return mediumThreshold;
    }
    public void setMediumThreshold(double mediumThreshold) {
        this.mediumThreshold = mediumThreshold;
    }
}