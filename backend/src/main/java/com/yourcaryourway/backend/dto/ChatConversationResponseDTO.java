package com.yourcaryourway.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatConversationResponseDTO {
    private Long conversationId;
    private String status;
    private LocalDateTime lastMessageTime;
    private Long userId;
    private String userFirstName;
    private String userLastName;
}