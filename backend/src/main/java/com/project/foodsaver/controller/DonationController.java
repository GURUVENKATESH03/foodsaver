package com.project.foodsaver.controller;

import com.project.foodsaver.dto.DonationListResponse;
import com.project.foodsaver.dto.DonationRequest;
import com.project.foodsaver.dto.DonationResponse;
import com.project.foodsaver.security.JwtUtils;
import com.project.foodsaver.service.DonationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    private final DonationService donationService;
    private final JwtUtils jwtUtils;

    public DonationController(DonationService donationService, JwtUtils jwtUtils) {
        this.donationService = donationService;
        this.jwtUtils = jwtUtils;
    }

    // ──────────────────────────────────────────────
    // LIST DONATIONS (public)
    // GET /api/v1/donations?search=&categoryId=&status=&page=&limit=
    // ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<DonationListResponse> listDonations(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit) {

        log.info("Fetching donations: search={}, categoryId={}, status={}, page={}, limit={}",
                search, categoryId, status, page, limit);

        DonationListResponse response = donationService.listDonations(search, categoryId, status, page, limit);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // GET DONATION BY ID (public)
    // GET /api/v1/donations/{id}
    // ──────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonation(@PathVariable Long id) {
        log.info("Fetching donation by id: {}", id);
        DonationResponse response = donationService.getDonationById(id);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // CREATE DONATION (authenticated)
    // POST /api/v1/donations
    // ──────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(
            @Valid @RequestBody DonationRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String userEmail = extractEmail(authHeader);
        log.info("Creating donation for user: {}", userEmail);

        DonationResponse response = donationService.createDonation(request, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────────
    // UPDATE DONATION (owner only)
    // PUT /api/v1/donations/{id}
    // ──────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<DonationResponse> updateDonation(
            @PathVariable Long id,
            @Valid @RequestBody DonationRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String userEmail = extractEmail(authHeader);
        log.info("Updating donation id={} for user: {}", id, userEmail);

        DonationResponse response = donationService.updateDonation(id, request, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // DELETE DONATION (owner or admin)
    // DELETE /api/v1/donations/{id}
    // ──────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDonation(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String userEmail = extractEmail(authHeader);
        log.info("Deleting donation id={} by user: {}", id, userEmail);

        donationService.deleteDonation(id, userEmail);
        return ResponseEntity.ok(Map.of("message", "Donation deleted successfully"));
    }

    // ──────────────────────────────────────────────
    // UPDATE DONATION STATUS (owner only)
    // PATCH /api/v1/donations/{id}/status
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/status")
    public ResponseEntity<DonationResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {

        String userEmail = extractEmail(authHeader);
        String status = body.get("status");
        log.info("Updating donation id={} status to {} by user: {}", id, status, userEmail);

        DonationResponse response = donationService.updateStatus(id, status, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // GET MY DONATIONS (authenticated donor)
    // GET /api/v1/donations/mine
    // ──────────────────────────────────────────────
    @GetMapping("/mine")
    public ResponseEntity<DonationListResponse> getMyDonations(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit) {

        String userEmail = extractEmail(authHeader);
        log.info("Fetching my donations for user: {}", userEmail);

        DonationListResponse response = donationService.getMyDonations(userEmail, page, limit);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // FIND NEARBY DONATIONS (public)
    // GET /api/v1/donations/nearby?lat=&lng=&radius=
    // ──────────────────────────────────────────────
    @GetMapping("/nearby")
    public ResponseEntity<List<DonationResponse>> findNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "10") double radius) {

        log.info("Finding nearby donations: lat={}, lng={}, radius={}km", lat, lng, radius);
        List<DonationResponse> response = donationService.findNearby(lat, lng, radius);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // GET FOOD CATEGORIES (public)
    // GET /api/v1/donations/categories
    // ──────────────────────────────────────────────
    @GetMapping("/categories")
    public ResponseEntity<List<DonationService.CategoryResponse>> getCategories() {
        log.info("Fetching food categories");
        List<DonationService.CategoryResponse> categories = donationService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // ──────────────────────────────────────────────
    // HELPER: Extract email from JWT token
    // ──────────────────────────────────────────────
    private String extractEmail(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                return jwtUtils.getEmailFromToken(token);
            }
        }
        throw new RuntimeException("Invalid or missing authorization token");
    }
}
