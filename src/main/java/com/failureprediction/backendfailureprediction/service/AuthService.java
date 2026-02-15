package com.failureprediction.backendfailureprediction.service;

import com.failureprediction.backendfailureprediction.dto.auth.AuthResponse;
import com.failureprediction.backendfailureprediction.dto.auth.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    void register(String username, String password, String role);
}