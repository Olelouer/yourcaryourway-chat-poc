package com.yourcaryourway.backend.mapper;

import com.yourcaryourway.backend.dto.ChatMessageResponse;
import com.yourcaryourway.backend.model.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {
    public ChatMessageResponse chatMessageToDto(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }

        ChatMessageResponse dto = new ChatMessageResponse();
        dto.setId(chatMessage.getId());
        dto.setMessage(chatMessage.getMessage());
        dto.setSenderType(chatMessage.getSenderType());
        dto.setSenderName(chatMessage.getSenderName());
        dto.setSentAt(chatMessage.getSentAt());

        if ("user".equals(chatMessage.getSenderType())) {
            dto.setSenderId(chatMessage.getUser().getId());
        } else if ("agent".equals(chatMessage.getSenderType())) {
            dto.setSenderId(chatMessage.getAgent().getId());
        }

        return dto;
    }
}
