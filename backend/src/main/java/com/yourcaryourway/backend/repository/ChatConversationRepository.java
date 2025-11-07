package com.yourcaryourway.backend.repository;

import com.yourcaryourway.backend.model.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {

    List<ChatConversation> findByAgentIdOrderByUpdatedAtDesc(Long agentId);

    List<ChatConversation> findByAgentIdIsNullAndStatus(String status);

    Optional<ChatConversation> findByUserIdAndStatusNot(Long userId, String status);

    Optional<ChatConversation> findByUserIdAndAgentIdAndStatusNot(Long userId, Long agentId, String status);
}