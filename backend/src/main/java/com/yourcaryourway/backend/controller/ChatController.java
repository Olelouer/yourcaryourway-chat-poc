package com.yourcaryourway.backend.controller;

import com.yourcaryourway.backend.dto.ChatMessageResponse;
import com.yourcaryourway.backend.dto.SendMessageRequest;
import com.yourcaryourway.backend.model.Agent;
import com.yourcaryourway.backend.model.User;
import com.yourcaryourway.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Envoyer un message
     */
    @PostMapping("/messages")
    public ResponseEntity<ChatMessageResponse> sendMessage(@RequestBody SendMessageRequest request) {
        ChatMessageResponse response = chatService.sendMessage(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupérer les nouveaux messages (polling)
     */
    @GetMapping("/messages/new/{userId}")
    public ResponseEntity<List<ChatMessageResponse>> getNewMessages(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Long lastMessageId) {
        List<ChatMessageResponse> messages = chatService.getNewMessages(userId, lastMessageId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Récupérer l'historique complet d'une conversation
     */
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ChatMessageResponse>> getHistory(@PathVariable Long userId) {
        List<ChatMessageResponse> messages = chatService.getConversationHistory(userId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Récupérer tous les utilisateurs
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(chatService.getAllUsers());
    }

    /**
     * Récupérer tous les agents (pour la liste de sélection)
     */
    @GetMapping("/agents")
    public ResponseEntity<List<Agent>> getAllAgents() {
        return ResponseEntity.ok(chatService.getAllAgents());
    }
}