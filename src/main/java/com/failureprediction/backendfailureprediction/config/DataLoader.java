package com. failureprediction.backendfailureprediction.config;

import com.failureprediction.backendfailureprediction.entity.UserEntity;
import com. failureprediction.backendfailureprediction.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner loadUsers() {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder. encode("admin123")
                );
                        admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Admin created");
            }
            if (!userRepository.existsByUsername("user")) {
                UserEntity user = new UserEntity();
                user.setUsername("user");
                user. setPassword(
                        passwordEncoder.encode("user123")
                );
                        user.setRole("USER");
                userRepository.save(user);
                System.out.println("User created");
            }
        };
    }
}