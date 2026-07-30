package com.project.foodsaver.service;

import com.project.foodsaver.dto.DonationRequestDTO;
import com.project.foodsaver.entity.DonationEntity;
import com.project.foodsaver.entity.DonationRequestEntity;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.entity.NotificationEntity;
import com.project.foodsaver.repositories.DonationRepo;
import com.project.foodsaver.repositories.DonationRequestRepo;
import com.project.foodsaver.repositories.NotificationRepo;
import com.project.foodsaver.repositories.ProfileRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepo requestRepo;
    private final DonationRepo donationRepo;
    private final ProfileRepo profileRepo;
    private final NotificationRepo notificationRepo;

    // ──────────────────────────────────────────────
    // CREATE REQUEST (receiver claims a donation)
    // ──────────────────────────────────────────────
    @Transactional
    public DonationRequestDTO createRequest(Long donationId, String message, String userEmail) {
        ProfileEntity receiver = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DonationEntity donation = donationRepo.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (!"AVAILABLE".equals(donation.getStatus())) {
            throw new RuntimeException("This donation is no longer available");
        }

        // Check duplicate pending request
        if (requestRepo.existsByDonationAndReceiverAndStatus(donation, receiver, "PENDING")) {
            throw new RuntimeException("You already have a pending request for this donation");
        }

        DonationRequestEntity request = new DonationRequestEntity();
        request.setDonation(donation);
        request.setReceiver(receiver);
        request.setStatus("PENDING");
        request.setRequestMessage(message);
        request.setRequestedQuantity(1);
        request.setRequestedAt(LocalDateTime.now());
        request = requestRepo.save(request);

        // Notify donor
        createNotification(
                donation.getDonor(),
                "New Request",
                receiver.getFullName() + " wants to claim: " + donation.getTitle(),
                "request",
                "/donations/" + donation.getId()
        );

        log.info("Request created: id={}, donation={}, receiver={}", request.getId(), donationId, userEmail);
        return toDTO(request);
    }

    // ──────────────────────────────────────────────
    // APPROVE REQUEST (donor accepts)
    // ──────────────────────────────────────────────
    @Transactional
    public DonationRequestDTO approveRequest(Long requestId, String userEmail) {
        DonationRequestEntity request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        DonationEntity donation = request.getDonation();

        if (!donation.getDonor().getEmail().equals(userEmail)) {
            throw new RuntimeException("Only the donor can approve requests");
        }

        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException("Request is not in PENDING status");
        }

        request.setStatus("APPROVED");
        request.setApprovedAt(LocalDateTime.now());

        // Update donation status
        donation.setStatus("RESERVED");
        donationRepo.save(donation);

        request = requestRepo.save(request);

        // Notify receiver
        createNotification(
                request.getReceiver(),
                "Request Approved",
                "Your request for " + donation.getTitle() + " has been approved",
                "request",
                "/donations/" + donation.getId()
        );

        log.info("Request approved: id={}, by={}", requestId, userEmail);
        return toDTO(request);
    }

    // ──────────────────────────────────────────────
    // REJECT REQUEST (donor declines)
    // ──────────────────────────────────────────────
    @Transactional
    public DonationRequestDTO rejectRequest(Long requestId, String userEmail) {
        DonationRequestEntity request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        DonationEntity donation = request.getDonation();

        if (!donation.getDonor().getEmail().equals(userEmail)) {
            throw new RuntimeException("Only the donor can reject requests");
        }

        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException("Request is not in PENDING status");
        }

        request.setStatus("REJECTED");
        request.setRejectedAt(LocalDateTime.now());
        request = requestRepo.save(request);

        // Notify receiver
        createNotification(
                request.getReceiver(),
                "Request Declined",
                "Your request for " + donation.getTitle() + " was declined",
                "request",
                "/donations/" + donation.getId()
        );

        log.info("Request rejected: id={}, by={}", requestId, userEmail);
        return toDTO(request);
    }

    // ──────────────────────────────────────────────
    // COMPLETE REQUEST (pickup done)
    // ──────────────────────────────────────────────
    @Transactional
    public DonationRequestDTO completeRequest(Long requestId, String userEmail) {
        DonationRequestEntity request = requestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        DonationEntity donation = request.getDonation();

        if (!donation.getDonor().getEmail().equals(userEmail) &&
            !request.getReceiver().getEmail().equals(userEmail)) {
            throw new RuntimeException("Only participants can mark as completed");
        }

        if (!"APPROVED".equals(request.getStatus())) {
            throw new RuntimeException("Request must be approved first");
        }

        request.setStatus("COMPLETED");
        request.setCompletedAt(LocalDateTime.now());

        donation.setStatus("PICKED_UP");
        donationRepo.save(donation);

        request = requestRepo.save(request);

        log.info("Request completed: id={}", requestId);
        return toDTO(request);
    }

    // ──────────────────────────────────────────────
    // LIST REQUESTS FOR A DONATION
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<DonationRequestDTO> getRequestsForDonation(Long donationId, String userEmail) {
        DonationEntity donation = donationRepo.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        // Only donor or admin (simplified) can see all requests
        if (!donation.getDonor().getEmail().equals(userEmail)) {
            throw new RuntimeException("Only the donor can view requests");
        }

        return requestRepo.findByDonationAndStatus(donation, "PENDING").stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────
    // LIST MY REQUESTS (receiver's requests)
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<DonationRequestDTO> getMyRequests(String userEmail, int page, int limit) {
        ProfileEntity receiver = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "requestedAt"));
        Page<DonationRequestEntity> requests = requestRepo.findByReceiver(receiver, pageable);

        return requests.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────
    // HELPER: Create notification
    // ──────────────────────────────────────────────
    private void createNotification(ProfileEntity user, String title, String message, String type, String link) {
        try {
            NotificationEntity notification = new NotificationEntity();
            notification.setUser(user);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setType(type);
            notification.setLink(link);
            notification.setIsRead(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepo.save(notification);
        } catch (Exception e) {
            log.warn("Failed to create notification: {}", e.getMessage());
        }
    }

    // ──────────────────────────────────────────────
    // HELPER: Entity → DTO
    // ──────────────────────────────────────────────
    private DonationRequestDTO toDTO(DonationRequestEntity entity) {
        return DonationRequestDTO.builder()
                .id(entity.getId())
                .donationId(entity.getDonation().getId())
                .donationTitle(entity.getDonation().getTitle())
                .receiverId(entity.getReceiver().getId())
                .receiverName(entity.getReceiver().getFullName())
                .receiverEmail(entity.getReceiver().getEmail())
                .status(entity.getStatus())
                .message(entity.getRequestMessage())
                .requestedAt(entity.getRequestedAt())
                .approvedAt(entity.getApprovedAt())
                .rejectedAt(entity.getRejectedAt())
                .completedAt(entity.getCompletedAt())
                .build();
    }
}
