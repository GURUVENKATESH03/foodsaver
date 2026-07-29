package com.project.foodsaver.service;

import com.project.foodsaver.entity.AuditLogEntity;
import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.repositories.AuditLogRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuditLogService {

    private final AuditLogRepo auditLogRepo;

    public AuditLogService(AuditLogRepo auditLogRepo) {
        this.auditLogRepo = auditLogRepo;
    }

    @Transactional
    public void logAction(ProfileEntity user, String action, String description, String ipAddress) {
        AuditLogEntity auditLog = new AuditLogEntity();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setDescription(description);
        auditLog.setIpAddress(ipAddress);
        auditLogRepo.save(auditLog);
        log.debug("Audit log created: {} - {}", action, description);
    }
}