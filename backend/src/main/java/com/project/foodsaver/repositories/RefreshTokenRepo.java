package com.project.foodsaver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.foodsaver.entity.ProfileEntity;
import com.project.foodsaver.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Long>{
    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUser(ProfileEntity user);

    boolean existsByToken(String token);
}
