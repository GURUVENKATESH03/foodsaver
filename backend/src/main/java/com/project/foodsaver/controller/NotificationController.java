package com.project.foodsaver.controller;

import com.project.foodsaver.dto.NotificationDTO;
import com.project.foodsaver.security.JwtUtils;
import com.project.foodsaver.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtUtils jwtUtils;

    public NotificationController(NotificationService notificationService, JwtUtils jwtUtils) {
        this.notificationService = notificationService;
        this.jwtUtils = jwtUtils;
    }

    // ──────────────────────────────────────────────
    // LIST MY NOTIFICATIONS
    // GET /api/v1/notifications
    // ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        String userEmail = extractEmail(authHeader);
        List<NotificationDTO> response = notificationService.getMyNotifications(userEmail, page, limit);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // UNREAD COUNT
    // GET /api/v1/notifications/unread-count
    // ──────────────────────────────────────────────
    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        long count = notificationService.getUnreadCount(userEmail);
        return ResponseEntity.ok(Map.of("count", count));
    }

    // ──────────────────────────────────────────────
    // MARK AS READ
    // PATCH /api/v1/notifications/{id}/read
    // ──────────────────────────────────────────────
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        notificationService.markAsRead(id, userEmail);
        return ResponseEntity.ok().build();
    }

    // ──────────────────────────────────────────────
    // MARK ALL AS READ
    // PATCH /api/v1/notifications/read-all
    // ──────────────────────────────────────────────
    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        notificationService.markAllAsRead(userEmail);
        return ResponseEntity.ok().build();
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
