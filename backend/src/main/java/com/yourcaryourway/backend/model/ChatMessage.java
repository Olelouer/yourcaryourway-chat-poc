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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;

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
        if ("user".equals(senderType) && user != null) {
            return user.getFirstName() + " " + user.getLastName();
        }
        if ("agent".equals(senderType) && agent != null) {
            return agent.getFirstName() + " " + agent.getLastName();
        }
        if (user != null) return user.getFirstName() + " " + user.getLastName();
        if (agent != null) return agent.getFirstName() + " " + agent.getLastName();
        return "Unknown";
    }
}