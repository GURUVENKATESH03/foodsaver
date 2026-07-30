package com.project.foodsaver.repositories;

import com.project.foodsaver.entity.ConversationEntity;
import com.project.foodsaver.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageEntity, Long> {

    Page<MessageEntity> findByConversation(ConversationEntity conversation, Pageable pageable);

    List<MessageEntity> findByConversationAndIsReadFalseAndSenderIdNot(
            ConversationEntity conversation, Long senderId);

    long countByConversationAndIsReadFalseAndSenderIdNot(
            ConversationEntity conversation, Long senderId);
}
