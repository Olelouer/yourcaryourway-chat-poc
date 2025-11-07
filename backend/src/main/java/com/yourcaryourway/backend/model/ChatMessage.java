package com.yourcaryourway.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ChatConversation conversation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "sender_type", nullable = false, length = 10)
    private String senderType;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }

    public String getSenderName() {
        if (conversation == null) {
            return "Unknown";
        }

        if ("user".equals(senderType) && conversation.getUser() != null) {
            return conversation.getUser().getFirstName() + " " + conversation.getUser().getLastName();
        }
        if ("agent".equals(senderType) && conversation.getAgent() != null) {
            return conversation.getAgent().getFirstName() + " " + conversation.getAgent().getLastName();
        }

        if (conversation.getUser() != null) return conversation.getUser().getFirstName();
        return "Unknown";
    }
}