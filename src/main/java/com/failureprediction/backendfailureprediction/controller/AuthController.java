package com.failureprediction.backendfailureprediction.controller;

import com.failureprediction.backendfailureprediction.dto.auth.AuthResponse;
import com.failureprediction.backendfailureprediction.dto.auth.LoginRequest;
import com.failureprediction.backendfailureprediction.dto.auth.RegisterRequest;
import com.failureprediction.backendfailureprediction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        authService.register(username, password, role);
        return ResponseEntity.ok("User registered");
    }
}