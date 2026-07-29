package com.project.foodsaver.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private ProfileEntity donor;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FoodCategoryEntity category;

    private Integer quantity;

    @Column(name = "quantity_unit")
    private String quantityUnit;

    @Column(name = "cooked_time")
    private LocalDateTime cookedTime;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @Column(name = "pickup_address")
    private String pickupAddress;

    private String city;
    private String state;
    private String pincode;

    private Double latitude;
    private Double longitude;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String status = "AVAILABLE";

    @Column(name = "is_emergency")
    private Boolean isEmergency = false;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DonationRequestEntity> donationRequests = new ArrayList<>();

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConversationEntity> conversations = new ArrayList<>();

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReportEntity> reports = new ArrayList<>();

    @OneToOne(mappedBy = "donation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DonationMetricsEntity metrics;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}