package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginAttemptRepo extends JpaRepository<LoginAttemptEntity, Long> {

    List<LoginAttemptEntity> findByEmailAndAttemptTimeAfter(String email, LocalDateTime attemptTime);

    long countByEmailAndSuccessFalseAndAttemptTimeAfter(String email, LocalDateTime attemptTime);
}