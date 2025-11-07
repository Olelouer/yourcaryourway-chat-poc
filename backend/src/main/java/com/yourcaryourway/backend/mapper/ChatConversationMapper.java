package com.yourcaryourway.backend.mapper;

import com.yourcaryourway.backend.dto.ChatConversationResponseDTO;
import com.yourcaryourway.backend.model.ChatConversation;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir l'entité ChatConversation en DTO pour l'agent.
 */
@Component
public class ChatConversationMapper {

    public ChatConversationResponseDTO toDto(ChatConversation conversation) {
        if (conversation == null) {
            return null;
        }

        ChatConversationResponseDTO dto = new ChatConversationResponseDTO();
        dto.setConversationId(conversation.getId());
        dto.setStatus(conversation.getStatus());
        dto.setLastMessageTime(conversation.getUpdatedAt());

        if (conversation.getUser() != null) {
            dto.setUserId(conversation.getUser().getId());
            dto.setUserFirstName(conversation.getUser().getFirstName());
            dto.setUserLastName(conversation.getUser().getLastName());
        }

        return dto;
    }
}