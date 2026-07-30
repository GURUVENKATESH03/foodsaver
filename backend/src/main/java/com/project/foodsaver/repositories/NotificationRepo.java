package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.NotificationEntity;
import com.project.foodsaver.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

    Page<NotificationEntity> findByUserOrderByCreatedAtDesc(ProfileEntity user, Pageable pageable);

    List<NotificationEntity> findByUserAndIsReadFalse(ProfileEntity user);

    long countByUserAndIsReadFalse(ProfileEntity user);

    long countByUser(ProfileEntity user);
}
