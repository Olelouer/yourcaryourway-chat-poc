package com.yourcaryourway.backend.service;

import com.yourcaryourway.backend.dto.ChatMessageResponse;
import com.yourcaryourway.backend.dto.SendMessageRequest;
import com.yourcaryourway.backend.mapper.ChatMessageMapper;
import com.yourcaryourway.backend.model.Agent;
import com.yourcaryourway.backend.model.ChatMessage;
import com.yourcaryourway.backend.model.User;
import com.yourcaryourway.backend.repository.AgentRepository;
import com.yourcaryourway.backend.repository.ChatMessageRepository;
import com.yourcaryourway.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final ChatMessageMapper chatMessageMapper;

    /**
     * Envoyer un message
     */
    @Transactional
    public ChatMessageResponse sendMessage(SendMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(request.getMessage());
        chatMessage.setSenderType(request.getSenderType());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        chatMessage.setUser(user);
        Agent agent;
        if ("user".equals(request.getSenderType())) {
            agent = findLastAgentForUser(request.getUserId());
            if (agent == null) {
                agent = assignNewAgent();
            }
        } else {
            agent = agentRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));
        }

        chatMessage.setAgent(agent);

        ChatMessage saved = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.chatMessageToDto(saved);
    }

    /**
     * Récupérer  l'agent déjà en charge de cet utilisateur
     */
    private Agent findLastAgentForUser(Long userId) {
        return chatMessageRepository.findFirstByUserIdOrderByIdDesc(userId)
                .map(ChatMessage::getAgent)
                .orElse(null);
    }

    private Agent assignNewAgent() {
        return agentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No agents available"));
    }

    /**
     * Récupérer l'historique complet d'une conversation
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getConversationHistory(Long userId) {
        return chatMessageRepository.findByUserId(userId)
                .stream()
                .map(chatMessageMapper::chatMessageToDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les nouveaux messages depuis un certain ID (pour le polling)
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getNewMessages(Long userId, Long lastMessageId) {
        return chatMessageRepository.findNewMessages(userId, lastMessageId)
                .stream()
                .map(chatMessageMapper::chatMessageToDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupérer tous les agents
     */
    @Transactional(readOnly = true)
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
}