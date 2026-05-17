package com.project.foodsaver.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donation_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id", nullable = false)
    private DonationEntity donation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private ProfileEntity receiver;

    @Column(name = "requested_quantity")
    private Integer requestedQuantity;

    @Column(name = "request_message", columnDefinition = "TEXT")
    private String requestMessage;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "requested_at", updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToOne(mappedBy = "donationRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PickupTrackingEntity pickupTracking;

    @OneToOne(mappedBy = "donationRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RatingEntity rating;

    @PrePersist
    protected void onCreate() {
        requestedAt = LocalDateTime.now();
    }
}