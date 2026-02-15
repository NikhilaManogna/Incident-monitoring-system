package com.failureprediction.backendfailureprediction.service.impl;

import com.failureprediction.backendfailureprediction.dto.auth.AuthResponse;
import com.failureprediction.backendfailureprediction.dto.auth.LoginRequest;
import com.failureprediction.backendfailureprediction.dto.auth.RegisterRequest;
import com.failureprediction.backendfailureprediction.entity.UserEntity;
import com.failureprediction.backendfailureprediction.repository.UserRepository;
import com.failureprediction.backendfailureprediction.security.JwtUtil;
import com.failureprediction.backendfailureprediction.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new AuthResponse(token);
    }

    @Override
    public void register(
            String username,
            String password,
            String role) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // BCrypt
                .role(role.toUpperCase()) // ROLE_ADMIN / ROLE_USER
                .build();

        userRepository.save(user);
    }
}