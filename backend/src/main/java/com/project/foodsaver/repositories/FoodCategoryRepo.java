package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCategoryRepo extends JpaRepository<FoodCategoryEntity, Long> {

    Optional<FoodCategoryEntity> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
}
