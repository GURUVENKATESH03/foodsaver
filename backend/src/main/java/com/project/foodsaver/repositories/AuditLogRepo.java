package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepo extends JpaRepository<AuditLogEntity, Long> {
}