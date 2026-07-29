package com.project.foodsaver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.foodsaver.entity.ProfileEntity;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileEntity, Long> {
    Optional<ProfileEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<ProfileEntity> findByEmailAndIsActiveTrue(String email);
}
