package com.project.foodsaver.controller;

import com.project.foodsaver.dto.DonationRequestDTO;
import com.project.foodsaver.security.JwtUtils;
import com.project.foodsaver.service.DonationRequestService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.foodsaver.dto.CreateRequestDTO;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/requests")
public class DonationRequestController {

    private final DonationRequestService requestService;
    private final JwtUtils jwtUtils;

    public DonationRequestController(DonationRequestService requestService, JwtUtils jwtUtils) {
        this.requestService = requestService;
        this.jwtUtils = jwtUtils;
    }

    // ──────────────────────────────────────────────
    // CLAIM A DONATION (receiver)
    // POST /api/v1/requests
    // ──────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<DonationRequestDTO> createRequest(
            @Valid @RequestBody CreateRequestDTO body,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        DonationRequestDTO response = requestService.createRequest(body.getDonationId(), body.getMessage(), userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────────
    // APPROVE REQUEST (donor)
    // PATCH /api/v1/requests/{id}/approve
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/approve")
    public ResponseEntity<DonationRequestDTO> approveRequest(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        DonationRequestDTO response = requestService.approveRequest(id, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // REJECT REQUEST (donor)
    // PATCH /api/v1/requests/{id}/reject
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/reject")
    public ResponseEntity<DonationRequestDTO> rejectRequest(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        DonationRequestDTO response = requestService.rejectRequest(id, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // COMPLETE REQUEST (either party)
    // PATCH /api/v1/requests/{id}/complete
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/complete")
    public ResponseEntity<DonationRequestDTO> completeRequest(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        DonationRequestDTO response = requestService.completeRequest(id, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // LIST REQUESTS FOR A DONATION (donor only)
    // GET /api/v1/requests/donation/{donationId}
    // ──────────────────────────────────────────────
    @GetMapping("/donation/{donationId}")
    public ResponseEntity<List<DonationRequestDTO>> getRequestsForDonation(
            @PathVariable Long donationId,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        List<DonationRequestDTO> response = requestService.getRequestsForDonation(donationId, userEmail);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // LIST MY REQUESTS (receiver)
    // GET /api/v1/requests/mine
    // ──────────────────────────────────────────────
    @GetMapping("/mine")
    public ResponseEntity<List<DonationRequestDTO>> getMyRequests(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int limit) {
        String userEmail = extractEmail(authHeader);
        List<DonationRequestDTO> response = requestService.getMyRequests(userEmail, page, limit);
        return ResponseEntity.ok(response);
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
}
