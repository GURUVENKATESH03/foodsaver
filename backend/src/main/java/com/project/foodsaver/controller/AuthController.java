package com.project.foodsaver.controller;

import com.project.foodsaver.dto.AuthResponse;
import com.project.foodsaver.dto.LoginRequest;
import com.project.foodsaver.dto.RefreshTokenRequest;
import com.project.foodsaver.security.JwtUtils;
import com.project.foodsaver.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    // -----------------------------------
    // SIGNUP
    // -----------------------------------
    @PostMapping("/signup")
    public ResponseEntity<Map<String, AuthResponse>> signup( @RequestBody LoginRequest request) {
        log.info("Signup request for email: {}", request.getEmail());
        Map<String, AuthResponse> outputResponse = authService.signup(request);
        return ResponseEntity.ok(outputResponse);
    }

    // -----------------------------------
    // LOGIN
    // -----------------------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login( @RequestBody LoginRequest loginRequest,
                                               HttpServletRequest request) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());

        if (loginRequest.getIpAddress() == null) {
            loginRequest.setIpAddress(getClientIp(request));
        }
        if (loginRequest.getDeviceInfo() == null) {
            loginRequest.setDeviceInfo(request.getHeader("User-Agent"));
        }

        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    // -----------------------------------
    // REFRESH TOKEN
    // -----------------------------------
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Token refresh request received");
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    // -----------------------------------
    // LOGOUT
    // -----------------------------------
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                     @RequestBody RefreshTokenRequest request) {
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                email = jwtUtils.getEmailFromToken(token);
            }
        }

        authService.logout(email, request.getRefreshToken());
        return ResponseEntity.ok().body(Map.of("message", "Logged out successfully"));
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}