package com.project.foodsaver.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "login_attempts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private Boolean success;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "attempt_time", updatable = false)
    private LocalDateTime attemptTime;

    @PrePersist
    protected void onCreate() {
        attemptTime = LocalDateTime.now();
    }
}