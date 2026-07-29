package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.DonationEntity;
import com.project.foodsaver.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepo extends JpaRepository<DonationEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"donor", "category"})
    Optional<DonationEntity> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"donor", "category"})
    Page<DonationEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"donor", "category"})
    Page<DonationEntity> findByDonor(ProfileEntity donor, Pageable pageable);

    @EntityGraph(attributePaths = {"donor", "category"})
    Page<DonationEntity> findByStatus(String status, Pageable pageable);

    @EntityGraph(attributePaths = {"donor", "category"})
    Page<DonationEntity> findByCategoryId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"donor", "category"})
    Page<DonationEntity> findByDonorAndStatus(ProfileEntity donor, String status, Pageable pageable);

    long countByDonor(ProfileEntity donor);

    long countByStatus(String status);

    @Query("SELECT d FROM DonationEntity d WHERE d.status = 'AVAILABLE' " +
           "AND d.latitude IS NOT NULL AND d.longitude IS NOT NULL " +
           "ORDER BY d.createdAt DESC")
    List<DonationEntity> findAvailableWithLocation(Pageable pageable);

    @Query(value = "SELECT d.* FROM donations d " +
           "WHERE d.status = 'AVAILABLE' " +
           "AND d.latitude IS NOT NULL AND d.longitude IS NOT NULL " +
           "AND (6371 * acos(cos(radians(:lat)) * cos(radians(d.latitude)) * " +
           "cos(radians(d.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(d.latitude)))) <= :radius " +
           "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(d.latitude)) * " +
           "cos(radians(d.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(d.latitude)))) ASC",
           countQuery = "SELECT COUNT(*) FROM donations d " +
           "WHERE d.status = 'AVAILABLE' " +
           "AND d.latitude IS NOT NULL AND d.longitude IS NOT NULL " +
           "AND (6371 * acos(cos(radians(:lat)) * cos(radians(d.latitude)) * " +
           "cos(radians(d.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(d.latitude)))) <= :radius",
           nativeQuery = true)
    List<DonationEntity> findNearby(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radius);

    @EntityGraph(attributePaths = {"donor", "category"})
    @Query("SELECT d FROM DonationEntity d WHERE " +
           "(:search IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(d.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:categoryId IS NULL OR d.category.id = :categoryId) AND " +
           "(:status IS NULL OR d.status = :status) " +
           "ORDER BY d.createdAt DESC")
    Page<DonationEntity> searchDonations(@Param("search") String search,
                                          @Param("categoryId") Long categoryId,
                                          @Param("status") String status,
                                          Pageable pageable);
}
