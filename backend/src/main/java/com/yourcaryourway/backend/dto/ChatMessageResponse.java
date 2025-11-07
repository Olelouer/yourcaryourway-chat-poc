package com.yourcaryourway.backend.dto;

import com.yourcaryourway.backend.model.ChatMessage;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessageResponse {
    private Long id;
    private String message;
    private Long conversationId;
    private String senderType;
    private String senderName;
    private Long senderId;
    private LocalDateTime sentAt;
}