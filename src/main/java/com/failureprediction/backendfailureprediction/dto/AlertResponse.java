package com.failureprediction.backendfailureprediction.dto;

public class AlertResponse {
    private String serviceName;
    private boolean alert;
    private String message;
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public boolean isAlert() {
        return alert;
    }
    public void setAlert(boolean alert) {
        this.alert = alert;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}