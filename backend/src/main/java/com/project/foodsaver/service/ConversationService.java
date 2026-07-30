package com.project.foodsaver.service;

import com.project.foodsaver.dto.ConversationDTO;
import com.project.foodsaver.dto.MessageDTO;
import com.project.foodsaver.entity.*;
import com.project.foodsaver.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConversationService {

    private final ConversationRepo conversationRepo;
    private final MessageRepo messageRepo;
    private final ProfileRepo profileRepo;
    private final DonationRepo donationRepo;

    public ConversationService(ConversationRepo conversationRepo, MessageRepo messageRepo,
                                ProfileRepo profileRepo, DonationRepo donationRepo) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
        this.profileRepo = profileRepo;
        this.donationRepo = donationRepo;
    }

    @Transactional(readOnly = true)
    public List<ConversationDTO> getMyConversations(String userEmail, int page, int limit) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ConversationEntity> conversations = conversationRepo.findByParticipant(user, pageable);

        return conversations.getContent().stream()
                .map(c -> toConversationDTO(c, user))
                .collect(Collectors.toList());
    }

    @Transactional
    public ConversationDTO startOrGetConversation(Long donationId, String userEmail) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DonationEntity donation = donationRepo.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        // Determine the other party
        ProfileEntity donor = donation.getDonor();
        ProfileEntity receiver = user;
        if (donor.getId().equals(user.getId())) {
            throw new RuntimeException("Cannot start conversation with yourself");
        }

        // Check if conversation already exists
        var existing = conversationRepo.findByDonationIdAndDonorAndReceiver(donationId, donor, receiver);
        if (existing.isPresent()) {
            return toConversationDTO(existing.get(), user);
        }

        // Create new conversation
        ConversationEntity conversation = new ConversationEntity();
        conversation.setDonor(donor);
        conversation.setReceiver(receiver);
        conversation.setDonation(donation);
        conversation = conversationRepo.save(conversation);

        log.info("Conversation created: id={}, donation={}, donor={}, receiver={}",
                conversation.getId(), donationId, donor.getEmail(), receiver.getEmail());

        return toConversationDTO(conversation, user);
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> getMessages(Long conversationId, String userEmail, int page, int limit) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ConversationEntity conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // Verify user is a participant
        if (!conversation.getDonor().getId().equals(user.getId()) &&
            !conversation.getReceiver().getId().equals(user.getId())) {
            throw new RuntimeException("Not a participant of this conversation");
        }

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.ASC, "sentAt"));
        Page<MessageEntity> messages = messageRepo.findByConversation(conversation, pageable);

        return messages.getContent().stream()
                .map(this::toMessageDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageDTO sendMessage(Long conversationId, String content, String userEmail) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ConversationEntity conversation = conversationRepo.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        // Verify user is a participant
        if (!conversation.getDonor().getId().equals(user.getId()) &&
            !conversation.getReceiver().getId().equals(user.getId())) {
            throw new RuntimeException("Not a participant of this conversation");
        }

        MessageEntity message = new MessageEntity();
        message.setConversation(conversation);
        message.setSender(user);
        message.setMessage(content);
        message.setIsRead(false);
        message = messageRepo.save(message);

        log.info("Message sent: conversation={}, sender={}", conversationId, userEmail);
        return toMessageDTO(message);
    }

    private ConversationDTO toConversationDTO(ConversationEntity entity, ProfileEntity currentUser) {
        boolean isDonor = entity.getDonor().getId().equals(currentUser.getId());
        ProfileEntity other = isDonor ? entity.getReceiver() : entity.getDonor();

        long unread = messageRepo.countByConversationAndIsReadFalseAndSenderIdNot(entity, currentUser.getId());

        // Get last message
        String lastMsg = null;
        Pageable one = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "sentAt"));
        Page<MessageEntity> lastMessages = messageRepo.findByConversation(entity, one);
        if (lastMessages.hasContent()) {
            lastMsg = lastMessages.getContent().get(0).getMessage();
        }

        return ConversationDTO.builder()
                .id(entity.getId())
                .donationId(entity.getDonation().getId())
                .donationTitle(entity.getDonation().getTitle())
                .otherParticipantId(other.getId())
                .otherParticipantName(other.getFullName())
                .otherParticipantAvatar(other.getProfileImage())
                .lastMessage(lastMsg)
                .unreadCount((int) unread)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getCreatedAt())
                .build();
    }

    private MessageDTO toMessageDTO(MessageEntity entity) {
        return MessageDTO.builder()
                .id(entity.getId())
                .conversationId(entity.getConversation().getId())
                .senderId(entity.getSender().getId())
                .senderName(entity.getSender().getFullName())
                .content(entity.getMessage())
                .isRead(entity.getIsRead())
                .sentAt(entity.getSentAt())
                .build();
    }
}
