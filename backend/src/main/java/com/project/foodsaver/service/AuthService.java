package com.project.foodsaver.service;

import com.project.foodsaver.dto.AuthResponse;
import com.project.foodsaver.dto.LoginRequest;
import com.project.foodsaver.dto.RefreshTokenRequest;
import com.project.foodsaver.entity.*;
import com.project.foodsaver.repositories.*;
import com.project.foodsaver.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepo profileRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final LoginAttemptRepo loginAttemptRepo;
    private final AuditLogService auditLogService;

    @Autowired
    private RoleRepo roleRepo;
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 30;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                       PasswordEncoder passwordEncoder, ProfileRepo profileRepo,
                       RefreshTokenRepo refreshTokenRepo, LoginAttemptRepo loginAttemptRepo,
                       AuditLogService auditLogService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.profileRepo = profileRepo;
        this.refreshTokenRepo = refreshTokenRepo;
        this.loginAttemptRepo = loginAttemptRepo;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Check if account is locked
        Optional<ProfileEntity> profileOpt = profileRepo.findByEmail(email);
        if (profileOpt.isPresent()) {
            ProfileEntity profile = profileOpt.get();

            if (Boolean.TRUE.equals(profile.getAccountLocked())) {
                if (profile.getLastLoginAt() != null &&
                        profile.getLastLoginAt().plusMinutes(LOCK_DURATION_MINUTES).isAfter(LocalDateTime.now())) {
                    log.warn("Login attempt on locked account: {}", email);
                    recordLoginAttempt(email, false, loginRequest.getIpAddress(), loginRequest.getDeviceInfo());
                    throw new RuntimeException("Account is locked. Please try again after " + LOCK_DURATION_MINUTES + " minutes.");
                } else {
                    // Unlock account after lock duration
                    profile.setAccountLocked(false);
                    profile.setFailedLoginAttempts(0);
                    profileRepo.save(profile);
                }
            }
        }

        try {
            // Authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details
            ProfileEntity profile = profileOpt.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

            // Reset failed attempts on successful login
            profile.setFailedLoginAttempts(0);
            profile.setAccountLocked(false);
            profile.setLastLoginAt(LocalDateTime.now());
            profileRepo.save(profile);

            // Generate tokens
            String accessToken = jwtUtils.generateAccessToken(email, profile.getRole().getRoleName());
            String refreshToken = jwtUtils.generateRefreshToken(email);

            // Save refresh token
            saveRefreshToken(profile, refreshToken, loginRequest);

            // Record successful login
            recordLoginAttempt(email, true, loginRequest.getIpAddress(), loginRequest.getDeviceInfo());

            // Audit log
            auditLogService.logAction(profile, "LOGIN", "User logged in successfully", loginRequest.getIpAddress());

            log.info("User logged in successfully: {}", email);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtUtils.getExpirationTime())
                    .email(profile.getEmail())
                    .fullName(profile.getFullName())
                    .role(profile.getRole().getRoleName())
                    .message("Login successful")
                    .build();

        } catch (BadCredentialsException e) {
            handleFailedLogin(email, loginRequest);
            throw new BadCredentialsException("Invalid email or password");
        } catch (DisabledException e) {
            throw new RuntimeException("Account is disabled. Please contact support.");
        } catch (LockedException e) {
            throw new RuntimeException("Account is locked. Please try again later.");
        }
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepo.findByToken(refreshToken);
        if (tokenOpt.isEmpty() || Boolean.TRUE.equals(tokenOpt.get().getRevoked()) ||
                tokenOpt.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        RefreshTokenEntity tokenEntity = tokenOpt.get();
        ProfileEntity profile = tokenEntity.getUser();

        String newAccessToken = jwtUtils.generateAccessToken(profile.getEmail(), profile.getRole().getRoleName());
        String newRefreshToken = jwtUtils.generateRefreshToken(profile.getEmail());

        // Revoke old token and save new one
        tokenEntity.setRevoked(true);
        refreshTokenRepo.save(tokenEntity);

        saveRefreshToken(profile, newRefreshToken, null);

        log.info("Token refreshed for user: {}", profile.getEmail());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpirationTime())
                .email(profile.getEmail())
                .fullName(profile.getFullName())
                .role(profile.getRole().getRoleName())
                .message("Token refreshed successfully")
                .build();
    }

    @Transactional
    public void logout(String email, String refreshToken) {
        Optional<RefreshTokenEntity> tokenOpt = refreshTokenRepo.findByToken(refreshToken);
        tokenOpt.ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepo.save(token);
        });

        SecurityContextHolder.clearContext();
        log.info("User logged out: {}", email);
    }

    private void saveRefreshToken(ProfileEntity profile, String token, LoginRequest loginRequest) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(token);
        refreshTokenEntity.setUser(profile);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenEntity.setRevoked(false);
        if (loginRequest != null) {
            refreshTokenEntity.setDeviceInfo(loginRequest.getDeviceInfo());
            refreshTokenEntity.setIpAddress(loginRequest.getIpAddress());
        }
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private void handleFailedLogin(String email, LoginRequest loginRequest) {
        Optional<ProfileEntity> profileOpt = profileRepo.findByEmail(email);
        if (profileOpt.isPresent()) {
            ProfileEntity profile = profileOpt.get();
            int failedAttempts = (profile.getFailedLoginAttempts() != null ? profile.getFailedLoginAttempts() : 0) + 1;
            profile.setFailedLoginAttempts(failedAttempts);

            if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                profile.setAccountLocked(true);
                log.warn("Account locked due to too many failed attempts: {}", email);
            }

            profileRepo.save(profile);
        }

        recordLoginAttempt(email, false, loginRequest.getIpAddress(), loginRequest.getDeviceInfo());
        log.warn("Failed login attempt for email: {}", email);
    }

    @Transactional
    public Map<String,AuthResponse> signup(LoginRequest request) {
        // Check if email exists
        if (profileRepo.existsByEmail(request.getEmail())) {
            Logger.getAnonymousLogger().log(Level.parse("info"),"Email is aldready registered");
            return buildResponse(new AuthResponse());
        }

        // Determine role from request, default to USER
        String roleName = request.getRole();
        String resolvedRole = (roleName == null || roleName.isBlank()) ? "USER" : roleName;

        // Get or create role
        RoleEntity userRole = roleRepo.findByRoleName(resolvedRole)
            .orElseGet(() -> {
                RoleEntity newRole = new RoleEntity();
                newRole.setRoleName(resolvedRole);
                newRole.setRoleDescription(resolvedRole + " role");
                return roleRepo.save(newRole);
            });

        // Create new user
        ProfileEntity profile = new ProfileEntity();
        profile.setEmail(request.getEmail());
        profile.setPassword(passwordEncoder.encode(request.getPassword()));
        profile.setFullName(request.getName());
        profile.setRole(userRole);
        profile.setIsActive(true);
        profile.setIsVerified(false);
        profile.setFailedLoginAttempts(0);
        profile.setAccountLocked(false);

        profileRepo.save(profile);

        // Generate tokens
        String accessToken = jwtUtils.generateAccessToken(profile.getEmail(), profile.getRole().getRoleName());
        String refreshToken = jwtUtils.generateRefreshToken(profile.getEmail());

        saveRefreshToken(profile, refreshToken, null);

        auditLogService.logAction(profile, "SIGNUP", "New user registered", null);

        new AuthResponse();
        AuthResponse authresponse =  AuthResponse.builder().accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpirationTime())
                .email(profile.getEmail())
                .fullName(profile.getFullName())
                .role(profile.getRole().getRoleName())
                .message("Registration successful")
                .build();
        return buildResponse(authresponse);

    }

    private void recordLoginAttempt(String email, boolean success, String ipAddress, String deviceInfo) {
        LoginAttemptEntity attempt = new LoginAttemptEntity();
        attempt.setEmail(email);
        attempt.setSuccess(success);
        attempt.setIpAddress(ipAddress);
        attempt.setDeviceInfo(deviceInfo);
        loginAttemptRepo.save(attempt);
    }

    private Map<String, AuthResponse> buildResponse (AuthResponse  response){
        Map<String, AuthResponse> buildResponse = new HashMap<>();
        buildResponse.put("data", response);
        return buildResponse;
    }

    private void overrideIfPresent(AuthResponse response){

    }
}
