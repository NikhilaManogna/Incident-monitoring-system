package com.failureprediction.backendfailureprediction.service;

import org.springframework.stereotype.Component;

@Component
public class FailureScoreCalculator {

    public double cpuContribution(double cpu) {
        return cpu * 0.3;
    }

    public double memoryContribution(double memory) {
        return memory * 0.25;
    }

    public double diskContribution(double disk) {
        return disk * 0.2;
    }

    public double errorContribution(long requestCount, long errorCount) {
        if (requestCount == 0) return 0;

        double errorRate = ((double) errorCount / requestCount) * 100;

        if (errorRate > 5) return 30;
        if (errorRate > 2) return 20;
        if (errorRate > 1) return 10;

        return errorRate * 5;
    }

    public double totalScore(
            double cpu,
            double memory,
            double disk,
            long requestCount,
            long errorCount) {
        double score =
                cpuContribution(cpu)
                        + memoryContribution(memory)
                        + diskContribution(disk)
                        + errorContribution(requestCount, errorCount);

        if (cpu > 80 && memory > 75) {
            score += 15;
        }
        return Math.min(100, score);
    }
}