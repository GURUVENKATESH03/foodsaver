package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.ConversationEntity;
import com.project.foodsaver.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepo extends JpaRepository<ConversationEntity, Long> {

    @EntityGraph(attributePaths = {"donor", "receiver", "donation"})
    Optional<ConversationEntity> findById(Long id);

    @EntityGraph(attributePaths = {"donor", "receiver", "donation"})
    @Query("SELECT c FROM ConversationEntity c WHERE c.donor = :user OR c.receiver = :user ORDER BY c.createdAt DESC")
    Page<ConversationEntity> findByParticipant(@Param("user") ProfileEntity user, Pageable pageable);

    @EntityGraph(attributePaths = {"donor", "receiver", "donation"})
    Optional<ConversationEntity> findByDonationIdAndDonorAndReceiver(
            Long donationId, ProfileEntity donor, ProfileEntity receiver);

    boolean existsByDonationIdAndDonorAndReceiver(
            Long donationId, ProfileEntity donor, ProfileEntity receiver);
}
