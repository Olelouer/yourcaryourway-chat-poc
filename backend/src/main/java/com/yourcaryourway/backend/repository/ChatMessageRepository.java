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
    List<ChatMessage> findByConversationIdOrderBySentAtAsc(Long conversationId);

    @Query("SELECT m FROM ChatMessage m WHERE m.conversation.id = :conversationId AND m.id > :lastMessageId ORDER BY m.sentAt ASC")
    List<ChatMessage> findNewMessages(@Param("conversationId") Long conversationId, @Param("lastMessageId") Long lastMessageId);
}