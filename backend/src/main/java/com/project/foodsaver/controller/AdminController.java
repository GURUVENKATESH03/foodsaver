package com.project.foodsaver.controller;

import com.project.foodsaver.dto.ProfileDTO;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.entity.RoleEntity;
import com.project.foodsaver.repositories.*;
import com.project.foodsaver.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ProfileRepo profileRepo;
    private final DonationRepo donationRepo;
    private final DonationRequestRepo requestRepo;
    private final RoleRepo roleRepo;
    private final JwtUtils jwtUtils;

    public AdminController(ProfileRepo profileRepo, DonationRepo donationRepo,
                            DonationRequestRepo requestRepo, RoleRepo roleRepo,
                            JwtUtils jwtUtils) {
        this.profileRepo = profileRepo;
        this.donationRepo = donationRepo;
        this.requestRepo = requestRepo;
        this.roleRepo = roleRepo;
        this.jwtUtils = jwtUtils;
    }

    // ──────────────────────────────────────────────
    // DASHBOARD STATS
    // GET /api/v1/admin/stats
    // ──────────────────────────────────────────────
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(@RequestHeader("Authorization") String authHeader) {
        verifyAdmin(authHeader);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", profileRepo.count());
        stats.put("totalDonations", donationRepo.count());
        stats.put("pendingRequests", requestRepo.countByStatus("PENDING"));
        stats.put("activeDonations", donationRepo.countByStatus("AVAILABLE"));
        stats.put("completedDonations", donationRepo.countByStatus("PICKED_UP"));

        return ResponseEntity.ok(stats);
    }

    // ──────────────────────────────────────────────
    // LIST ALL USERS (paginated, filterable)
    // GET /api/v1/admin/users?search=&role=&status=&page=&limit=
    // ──────────────────────────────────────────────
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> listUsers(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        verifyAdmin(authHeader);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        // Simple search/filter — expand with JPA Specification if needed
        Page<ProfileEntity> users;
        if (search != null && !search.isBlank()) {
            // In production use Specification for combined search+filters
            users = profileRepo.findAll(pageable);
        } else {
            users = profileRepo.findAll(pageable);
        }

        List<ProfileDTO> data = users.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("page", page);
        response.put("total", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // UPDATE USER ROLE
    // PATCH /api/v1/admin/users/{id}/role
    // ──────────────────────────────────────────────
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ProfileDTO> updateUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {
        verifyAdmin(authHeader);

        ProfileEntity user = profileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = body.get("role");
        RoleEntity role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.setRole(role);
        profileRepo.save(user);

        log.info("User {} role updated to {} by admin", id, roleName);
        return ResponseEntity.ok(toDTO(user));
    }

    // ──────────────────────────────────────────────
    // TOGGLE USER ACTIVE STATUS
    // PATCH /api/v1/admin/users/{id}/toggle-status
    // ──────────────────────────────────────────────
    @PatchMapping("/users/{id}/toggle-status")
    public ResponseEntity<ProfileDTO> toggleUserStatus(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        verifyAdmin(authHeader);

        ProfileEntity user = profileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setIsActive(!Boolean.TRUE.equals(user.getIsActive()));
        profileRepo.save(user);

        log.info("User {} active status toggled to {} by admin", id, user.getIsActive());
        return ResponseEntity.ok(toDTO(user));
    }

    // ──────────────────────────────────────────────
    // DELETE USER
    // DELETE /api/v1/admin/users/{id}
    // ──────────────────────────────────────────────
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        verifyAdmin(authHeader);

        ProfileEntity user = profileRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileRepo.delete(user);
        log.info("User {} deleted by admin", id);

        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    // ──────────────────────────────────────────────
    // HELPER: Verify admin role
    // ──────────────────────────────────────────────
    private void verifyAdmin(String authHeader) {
        String email = extractEmail(authHeader);
        ProfileEntity user = profileRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            throw new RuntimeException("Admin access required");
        }
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
