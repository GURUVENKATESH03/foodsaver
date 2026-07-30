package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.DonationEntity;
import com.project.foodsaver.entity.DonationRequestEntity;
import com.project.foodsaver.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRequestRepo extends JpaRepository<DonationRequestEntity, Long> {

    @EntityGraph(attributePaths = {"donation", "receiver"})
    Optional<DonationRequestEntity> findById(Long id);

    @EntityGraph(attributePaths = {"donation", "receiver"})
    Page<DonationRequestEntity> findByDonation(DonationEntity donation, Pageable pageable);

    @EntityGraph(attributePaths = {"donation", "receiver"})
    Page<DonationRequestEntity> findByReceiver(ProfileEntity receiver, Pageable pageable);

    @EntityGraph(attributePaths = {"donation", "receiver"})
    Page<DonationRequestEntity> findByDonationAndStatus(DonationEntity donation, String status, Pageable pageable);

    @EntityGraph(attributePaths = {"donation", "receiver"})
    List<DonationRequestEntity> findByDonationAndStatus(DonationEntity donation, String status);

    long countByDonation(DonationEntity donation);

    long countByStatus(String status);

    boolean existsByDonationAndReceiverAndStatus(DonationEntity donation, ProfileEntity receiver, String status);
}
