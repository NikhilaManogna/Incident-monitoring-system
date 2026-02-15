package com.failureprediction.backendfailureprediction.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex
    ) throws IOException {
        response.setStatus(403);
        response.setContentType("application/json");
        response.getWriter().write("""
        {
          "success": false,
          "message": "Access Denied: Insufficient Permission"
        }
        """);
    }
}