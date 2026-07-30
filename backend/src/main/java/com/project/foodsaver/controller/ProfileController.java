package com.project.foodsaver.controller;

import com.project.foodsaver.dto.ProfileDTO;
import com.project.foodsaver.dto.ProfileUpdateRequest;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.repositories.ProfileRepo;
import com.project.foodsaver.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final ProfileRepo profileRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(ProfileRepo profileRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.profileRepo = profileRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    // ──────────────────────────────────────────────
    // GET CURRENT USER PROFILE
    // GET /api/v1/profile
    // ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<ProfileDTO> getProfile(@RequestHeader("Authorization") String authHeader) {
        ProfileEntity profile = getCurrentUser(authHeader);
        return ResponseEntity.ok(toDTO(profile));
    }

    // ──────────────────────────────────────────────
    // UPDATE PROFILE
    // PUT /api/v1/profile
    // ──────────────────────────────────────────────
    @PutMapping
    public ResponseEntity<ProfileDTO> updateProfile(
            @RequestBody ProfileUpdateRequest request,
            @RequestHeader("Authorization") String authHeader) {
        ProfileEntity profile = getCurrentUser(authHeader);

        if (request.getFullName() != null) profile.setFullName(request.getFullName());
        if (request.getPhoneNumber() != null) profile.setPhoneNumber(request.getPhoneNumber());
        if (request.getOrganizationName() != null) profile.setOrganizationName(request.getOrganizationName());
        if (request.getAddress() != null) profile.setAddress(request.getAddress());
        if (request.getCity() != null) profile.setCity(request.getCity());
        if (request.getState() != null) profile.setState(request.getState());
        if (request.getPincode() != null) profile.setPincode(request.getPincode());
        if (request.getLatitude() != null) profile.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) profile.setLongitude(request.getLongitude());
        if (request.getProfileImage() != null) profile.setProfileImage(request.getProfileImage());

        profile = profileRepo.save(profile);
        log.info("Profile updated: {}", profile.getEmail());
        return ResponseEntity.ok(toDTO(profile));
    }

    // ──────────────────────────────────────────────
    // CHANGE PASSWORD
    // PATCH /api/v1/profile/password
    // ──────────────────────────────────────────────
    @PatchMapping("/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {
        ProfileEntity profile = getCurrentUser(authHeader);

        String currentPassword = body.get("currentPassword");
        String newPassword = body.get("newPassword");

        if (currentPassword == null || newPassword == null) {
            throw new RuntimeException("Current and new password are required");
        }

        if (!passwordEncoder.matches(currentPassword, profile.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        profile.setPassword(passwordEncoder.encode(newPassword));
        profileRepo.save(profile);
        log.info("Password changed for: {}", profile.getEmail());

        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    private ProfileEntity getCurrentUser(String authHeader) {
        String email = extractEmail(authHeader);
        return profileRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String extractEmail(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                return jwtUtils.getEmailFromToken(token);
            }
        }
        throw new RuntimeException("Invalid or missing authorization token");
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        return ProfileDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .profileImage(entity.getProfileImage())
                .organizationName(entity.getOrganizationName())
                .address(entity.getAddress())
                .city(entity.getCity())
                .state(entity.getState())
                .pincode(entity.getPincode())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .role(entity.getRole() != null ? entity.getRole().getRoleName() : null)
                .isVerified(entity.getIsVerified())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
