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
public class DonationResponse {

    private Long id;
    private Long donorId;
    private String donorName;
    private String donorAvatar;
    private String title;
    private String description;
    private Long categoryId;
    private String categoryName;
    private String categoryIcon;
    private Integer quantity;
    private String quantityUnit;
    private LocalDateTime expiryTime;
    private String pickupAddress;
    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private String imageUrl;
    private String status;
    private Boolean isEmergency;
    private Integer requestsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
