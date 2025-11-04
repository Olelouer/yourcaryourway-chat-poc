package com.yourcaryourway.backend.dto;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long userId;
    private Long agentId;
    private String message;
    private String senderType;
}