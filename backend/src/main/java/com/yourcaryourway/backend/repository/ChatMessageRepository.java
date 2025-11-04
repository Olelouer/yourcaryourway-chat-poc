package com.yourcaryourway.backend.repository;

import com.yourcaryourway.backend.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    /**
     * Récupérer tous les messages d'une conversation (user + agent)
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.user.id = :userId ORDER BY m.sentAt ASC")
    List<ChatMessage> findByUserId(@Param("userId") Long userId);

    /**
     * Récupérer les nouveaux messages depuis un certain ID
     */
    @Query("SELECT m FROM ChatMessage m WHERE m.user.id = :userId AND m.id > :lastMessageId ORDER BY m.sentAt ASC")
    List<ChatMessage> findNewMessages(@Param("userId") Long userId, @Param("lastMessageId") Long lastMessageId);

    /**
     * Récupère le tout dernier message envoyé dans une conversation
     */
    Optional<ChatMessage> findFirstByUserIdOrderByIdDesc(Long userId);
}