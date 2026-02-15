package com.failureprediction.backendfailureprediction.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class SystemMetricRequest {
    @NotBlank
    private String serviceName;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Float cpuUsage;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Float memoryUsage;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Float diskUsage;
    @Min(0)
    private long requestCount;
    @Min(0)
    private long errorCount;
    private LocalDateTime timestamp;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public Float getCpuUsage() {
        return cpuUsage;
    }
    public void setCpuUsage(Float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }
    public Float getMemoryUsage() {
        return memoryUsage;
    }
    public void setMemoryUsage(Float memoryUsage) {
        this.memoryUsage = memoryUsage;
    }
    public Float getDiskUsage() {
        return diskUsage;
    }
    public void setDiskUsage(Float diskUsage) {
        this.diskUsage = diskUsage;
    }
    public long getRequestCount() {
        return requestCount;
    }
    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }
    public long getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}