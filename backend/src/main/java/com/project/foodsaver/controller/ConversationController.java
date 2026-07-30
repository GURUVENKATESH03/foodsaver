package com.project.foodsaver.controller;

import com.project.foodsaver.dto.ConversationDTO;
import com.project.foodsaver.dto.MessageDTO;
import com.project.foodsaver.security.JwtUtils;
import com.project.foodsaver.service.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final JwtUtils jwtUtils;

    public ConversationController(ConversationService conversationService, JwtUtils jwtUtils) {
        this.conversationService = conversationService;
        this.jwtUtils = jwtUtils;
    }

    // ──────────────────────────────────────────────
    // LIST MY CONVERSATIONS
    // GET /api/v1/conversations
    // ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getConversations(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        String userEmail = extractEmail(authHeader);
        List<ConversationDTO> response = conversationService.getMyConversations(userEmail, page, limit);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // START OR GET EXISTING CONVERSATION
    // POST /api/v1/conversations
    // ──────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<ConversationDTO> startConversation(
            @RequestBody Map<String, Long> body,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        Long donationId = body.get("donationId");
        ConversationDTO response = conversationService.startOrGetConversation(donationId, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────────
    // GET MESSAGES FOR A CONVERSATION
    // GET /api/v1/conversations/{id}/messages
    // ──────────────────────────────────────────────
    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageDTO>> getMessages(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int limit) {
        String userEmail = extractEmail(authHeader);
        List<MessageDTO> response = conversationService.getMessages(id, userEmail, page, limit);
        return ResponseEntity.ok(response);
    }

    // ──────────────────────────────────────────────
    // SEND A MESSAGE
    // POST /api/v1/conversations/{id}/messages
    // ──────────────────────────────────────────────
    @PostMapping("/{id}/messages")
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader) {
        String userEmail = extractEmail(authHeader);
        String content = body.get("content");
        if (content == null || content.isBlank()) {
            throw new RuntimeException("Message content is required");
        }
        MessageDTO response = conversationService.sendMessage(id, content, userEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
