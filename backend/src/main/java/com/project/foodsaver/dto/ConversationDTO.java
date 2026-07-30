package com.project.foodsaver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDTO {
    private Long id;
    private Long donationId;
    private String donationTitle;
    private Long otherParticipantId;
    private String otherParticipantName;
    private String otherParticipantAvatar;
    private String lastMessage;
    private int unreadCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
