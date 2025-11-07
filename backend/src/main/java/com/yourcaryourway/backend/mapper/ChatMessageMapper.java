package com.yourcaryourway.backend.mapper;

import com.yourcaryourway.backend.dto.ChatMessageResponse;
import com.yourcaryourway.backend.model.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {
    public ChatMessageResponse chatMessageToDto(ChatMessage chatMessage) {
        if (chatMessage == null || chatMessage.getConversation() == null) {
            return null;
        }

        ChatMessageResponse dto = new ChatMessageResponse();

        dto.setId(chatMessage.getId());
        dto.setConversationId(chatMessage.getConversation().getId());
        dto.setMessage(chatMessage.getMessage());

        dto.setSentAt(chatMessage.getSentAt());
        dto.setSenderType(chatMessage.getSenderType());

        dto.setSenderName(chatMessage.getSenderName());

        if ("user".equals(chatMessage.getSenderType())) {
            dto.setSenderId(chatMessage.getConversation().getUser().getId());
        } else if ("agent".equals(chatMessage.getSenderType())) {
            if (chatMessage.getConversation().getAgent() != null) {
                dto.setSenderId(chatMessage.getConversation().getAgent().getId());
            }
        }

        return dto;
    }
}