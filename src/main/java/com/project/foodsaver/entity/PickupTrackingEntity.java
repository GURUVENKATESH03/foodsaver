package com.project.foodsaver.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickup_tracking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickupTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_request_id", nullable = false)
    private DonationRequestEntity donationRequest;

    @Column(name = "pickup_person_name")
    private String pickupPersonName;

    @Column(name = "pickup_phone")
    private String pickupPhone;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "pickup_status")
    private String pickupStatus = "PENDING";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}