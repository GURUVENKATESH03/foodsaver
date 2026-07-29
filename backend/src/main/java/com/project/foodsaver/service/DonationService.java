package com.project.foodsaver.service;

import com.project.foodsaver.dto.DonationListResponse;
import com.project.foodsaver.dto.DonationRequest;
import com.project.foodsaver.dto.DonationResponse;
import com.project.foodsaver.entity.DonationEntity;
import com.project.foodsaver.entity.FoodCategoryEntity;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.repositories.DonationRepo;
import com.project.foodsaver.repositories.FoodCategoryRepo;
import com.project.foodsaver.repositories.ProfileRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DonationService {

    private final DonationRepo donationRepo;
    private final FoodCategoryRepo foodCategoryRepo;
    private final ProfileRepo profileRepo;

    public DonationService(DonationRepo donationRepo, FoodCategoryRepo foodCategoryRepo, ProfileRepo profileRepo) {
        this.donationRepo = donationRepo;
        this.foodCategoryRepo = foodCategoryRepo;
        this.profileRepo = profileRepo;
    }

    // ──────────────────────────────────────────────
    // LIST DONATIONS (paginated, filterable)
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public DonationListResponse listDonations(String search, Long categoryId, String status,
                                               int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<DonationEntity> donationPage;

        if (search != null || categoryId != null || status != null) {
            String searchParam = (search != null && !search.isBlank()) ? search : null;
            Long catParam = categoryId;
            String statusParam = (status != null && !status.isBlank()) ? status : null;
            donationPage = donationRepo.searchDonations(searchParam, catParam, statusParam, pageable);
        } else {
            donationPage = donationRepo.findAll(pageable);
        }

        List<DonationResponse> data = donationPage.getContent().stream()
                .map(this::toDonationResponse)
                .collect(Collectors.toList());

        return DonationListResponse.builder()
                .data(data)
                .page(page)
                .limit(limit)
                .total(donationPage.getTotalElements())
                .totalPages(donationPage.getTotalPages())
                .build();
    }

    // ──────────────────────────────────────────────
    // GET SINGLE DONATION BY ID
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public DonationResponse getDonationById(Long id) {
        DonationEntity donation = donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        return toDonationResponse(donation);
    }

    // ──────────────────────────────────────────────
    // CREATE DONATION
    // ──────────────────────────────────────────────
    @Transactional
    public DonationResponse createDonation(DonationRequest request, String userEmail) {
        ProfileEntity donor = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        FoodCategoryEntity category = foodCategoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        DonationEntity donation = new DonationEntity();
        donation.setDonor(donor);
        donation.setTitle(request.getTitle());
        donation.setDescription(request.getDescription());
        donation.setCategory(category);
        donation.setQuantity(request.getQuantity());
        donation.setQuantityUnit(request.getQuantityUnit());
        donation.setPickupAddress(request.getPickupAddress());
        donation.setCity(request.getCity());
        donation.setState(request.getState());
        donation.setPincode(request.getPincode());
        donation.setLatitude(request.getLatitude());
        donation.setLongitude(request.getLongitude());
        donation.setImageUrl(request.getImageUrl());
        donation.setStatus("AVAILABLE");
        donation.setIsEmergency(request.getIsEmergency() != null ? request.getIsEmergency() : false);

        if (request.getExpiryTime() != null && !request.getExpiryTime().isBlank()) {
            donation.setExpiryTime(LocalDateTime.parse(request.getExpiryTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        DonationEntity saved = donationRepo.save(donation);
        log.info("Donation created: id={}, title={}, donor={}", saved.getId(), saved.getTitle(), userEmail);

        return toDonationResponse(saved);
    }

    // ──────────────────────────────────────────────
    // UPDATE DONATION
    // ──────────────────────────────────────────────
    @Transactional
    public DonationResponse updateDonation(Long id, DonationRequest request, String userEmail) {
        DonationEntity donation = donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        // Verify ownership
        if (!donation.getDonor().getEmail().equals(userEmail)) {
            throw new RuntimeException("You do not have permission to update this donation");
        }

        // Only allow updates if donation is still AVAILABLE
        if (!"AVAILABLE".equals(donation.getStatus())) {
            throw new RuntimeException("Cannot update a donation that is " + donation.getStatus());
        }

        if (request.getCategoryId() != null) {
            FoodCategoryEntity category = foodCategoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            donation.setCategory(category);
        }

        if (request.getTitle() != null) donation.setTitle(request.getTitle());
        if (request.getDescription() != null) donation.setDescription(request.getDescription());
        if (request.getQuantity() != null) donation.setQuantity(request.getQuantity());
        if (request.getQuantityUnit() != null) donation.setQuantityUnit(request.getQuantityUnit());
        if (request.getPickupAddress() != null) donation.setPickupAddress(request.getPickupAddress());
        if (request.getCity() != null) donation.setCity(request.getCity());
        if (request.getState() != null) donation.setState(request.getState());
        if (request.getPincode() != null) donation.setPincode(request.getPincode());
        if (request.getLatitude() != null) donation.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) donation.setLongitude(request.getLongitude());
        if (request.getImageUrl() != null) donation.setImageUrl(request.getImageUrl());
        if (request.getIsEmergency() != null) donation.setIsEmergency(request.getIsEmergency());

        if (request.getExpiryTime() != null && !request.getExpiryTime().isBlank()) {
            donation.setExpiryTime(LocalDateTime.parse(request.getExpiryTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        DonationEntity saved = donationRepo.save(donation);
        log.info("Donation updated: id={}", saved.getId());

        return toDonationResponse(saved);
    }

    // ──────────────────────────────────────────────
    // DELETE DONATION
    // ──────────────────────────────────────────────
    @Transactional
    public void deleteDonation(Long id, String userEmail) {
        DonationEntity donation = donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        // Verify ownership or admin
        if (!donation.getDonor().getEmail().equals(userEmail)) {
            // Check if user is admin (simplified — in production, check role properly)
            ProfileEntity user = profileRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (!"ADMIN".equals(user.getRole().getRoleName())) {
                throw new RuntimeException("You do not have permission to delete this donation");
            }
        }

        donationRepo.delete(donation);
        log.info("Donation deleted: id={}", id);
    }

    // ──────────────────────────────────────────────
    // UPDATE DONATION STATUS
    // ──────────────────────────────────────────────
    @Transactional
    public DonationResponse updateStatus(Long id, String status, String userEmail) {
        DonationEntity donation = donationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        if (!donation.getDonor().getEmail().equals(userEmail)) {
            throw new RuntimeException("You do not have permission to update this donation's status");
        }

        List<String> validStatuses = List.of("AVAILABLE", "RESERVED", "PICKED_UP", "EXPIRED", "CANCELLED");
        if (!validStatuses.contains(status)) {
            throw new RuntimeException("Invalid status: " + status);
        }

        donation.setStatus(status);
        DonationEntity saved = donationRepo.save(donation);
        log.info("Donation status updated: id={}, status={}", id, status);

        return toDonationResponse(saved);
    }

    // ──────────────────────────────────────────────
    // GET DONOR'S DONATIONS
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public DonationListResponse getMyDonations(String userEmail, int page, int limit) {
        ProfileEntity donor = profileRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<DonationEntity> donationPage = donationRepo.findByDonor(donor, pageable);

        List<DonationResponse> data = donationPage.getContent().stream()
                .map(this::toDonationResponse)
                .collect(Collectors.toList());

        return DonationListResponse.builder()
                .data(data)
                .page(page)
                .limit(limit)
                .total(donationPage.getTotalElements())
                .totalPages(donationPage.getTotalPages())
                .build();
    }

    // ──────────────────────────────────────────────
    // FIND NEARBY DONATIONS (Haversine)
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<DonationResponse> findNearby(double lat, double lng, double radius) {
        List<DonationEntity> nearby = donationRepo.findNearby(lat, lng, radius);

        return nearby.stream()
                .map(donation -> {
                    DonationResponse response = toDonationResponse(donation);
                    // Calculate exact distance for each
                    double dist = calculateDistance(lat, lng,
                            donation.getLatitude(), donation.getLongitude());
                    response.setDistance(Math.round(dist * 10.0) / 10.0);
                    return response;
                })
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────────
    // GET ALL FOOD CATEGORIES
    // ──────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        List<FoodCategoryEntity> categories = foodCategoryRepo.findAll();
        List<CategoryResponse> result = new ArrayList<>();

        for (FoodCategoryEntity cat : categories) {
            result.add(new CategoryResponse(cat.getId(), cat.getCategoryName(), cat.getDescription()));
        }
        return result;
    }

    // ──────────────────────────────────────────────
    // CategoryResponse inner DTO
    // ──────────────────────────────────────────────
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryResponse {
        private Long id;
        private String categoryName;
        private String description;
    }

    // ──────────────────────────────────────────────
    // HELPER: Map Entity → Response DTO
    // ──────────────────────────────────────────────
    private DonationResponse toDonationResponse(DonationEntity donation) {
        DonationResponse response = DonationResponse.builder()
                .id(donation.getId())
                .donorId(donation.getDonor().getId())
                .donorName(donation.getDonor().getFullName() != null ?
                        donation.getDonor().getFullName() : donation.getDonor().getEmail())
                .donorAvatar(donation.getDonor().getProfileImage())
                .title(donation.getTitle())
                .description(donation.getDescription())
                .quantity(donation.getQuantity())
                .quantityUnit(donation.getQuantityUnit())
                .expiryTime(donation.getExpiryTime())
                .pickupAddress(donation.getPickupAddress())
                .city(donation.getCity())
                .state(donation.getState())
                .pincode(donation.getPincode())
                .latitude(donation.getLatitude())
                .longitude(donation.getLongitude())
                .imageUrl(donation.getImageUrl())
                .status(donation.getStatus())
                .isEmergency(donation.getIsEmergency())
                .requestsCount(donation.getDonationRequests() != null ?
                        donation.getDonationRequests().size() : 0)
                .createdAt(donation.getCreatedAt())
                .updatedAt(donation.getUpdatedAt())
                .build();

        if (donation.getCategory() != null) {
            response.setCategoryId(donation.getCategory().getId());
            response.setCategoryName(donation.getCategory().getCategoryName());
        }

        return response;
    }

    // ──────────────────────────────────────────────
    // HELPER: Haversine distance calculation
    // ──────────────────────────────────────────────
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
