package com.project.foodsaver.service;

import com.project.foodsaver.dto.NotificationDTO;
import com.project.foodsaver.entity.NotificationEntity;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.repositories.NotificationRepo;
import com.project.foodsaver.repositories.ProfileRepo;
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
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final ProfileRepo profileRepo;

    public NotificationService(NotificationRepo notificationRepo, ProfileRepo profileRepo) {
        this.notificationRepo = notificationRepo;
        this.profileRepo = profileRepo;
    }

    @Transactional(readOnly = true)
    public List<NotificationDTO> getMyNotifications(String userEmail, int page, int limit) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NotificationEntity> notifications = notificationRepo.findByUserOrderByCreatedAtDesc(user, pageable);

        return notifications.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getUnreadCount(String userEmail) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepo.countByUserAndIsReadFalse(user);
    }

    @Transactional
    public void markAsRead(Long notificationId, String userEmail) {
        NotificationEntity notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Not authorized");
        }

        notification.setIsRead(true);
        notificationRepo.save(notification);
    }

    @Transactional
    public void markAllAsRead(String userEmail) {
        ProfileEntity user = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<NotificationEntity> unread = notificationRepo.findByUserAndIsReadFalse(user);
        unread.forEach(n -> n.setIsRead(true));
        notificationRepo.saveAll(unread);
    }

    private NotificationDTO toDTO(NotificationEntity entity) {
        return NotificationDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .type(entity.getType())
                .isRead(entity.getIsRead())
                .link(entity.getLink())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
