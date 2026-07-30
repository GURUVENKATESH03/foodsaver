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
public class ProfileDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private String organizationName;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private String role;
    private Boolean isVerified;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
