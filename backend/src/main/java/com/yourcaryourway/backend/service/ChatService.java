package com.yourcaryourway.backend.service;

import com.yourcaryourway.backend.dto.ChatConversationResponseDTO;
import com.yourcaryourway.backend.dto.ChatMessageResponse;
import com.yourcaryourway.backend.dto.SendMessageRequest;
import com.yourcaryourway.backend.mapper.ChatConversationMapper;
import com.yourcaryourway.backend.mapper.ChatMessageMapper;
import com.yourcaryourway.backend.model.Agent;
import com.yourcaryourway.backend.model.ChatConversation; // <-- NOUVEAU
import com.yourcaryourway.backend.model.ChatMessage;
import com.yourcaryourway.backend.model.User;
import com.yourcaryourway.backend.repository.AgentRepository;
import com.yourcaryourway.backend.repository.ChatConversationRepository; // <-- NOUVEAU
import com.yourcaryourway.backend.repository.ChatMessageRepository;
import com.yourcaryourway.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatConversationRepository chatConversationRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatConversationMapper chatConversationMapper;

    /**
     * Envoyer un message (Logique 2.0)
     */
    @Transactional
    public ChatMessageResponse sendMessage(SendMessageRequest request) {

        ChatConversation conversation = findOrCreateConversation(request);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversation(conversation);
        chatMessage.setMessage(request.getMessage());
        chatMessage.setSenderType(request.getSenderType());

        ChatMessage saved = chatMessageRepository.save(chatMessage);

        if ("user".equals(request.getSenderType())) {
            conversation.setStatus("pending_agent");
        } else {
            conversation.setStatus("pending_user");
        }
        chatConversationRepository.save(conversation);

        return chatMessageMapper.chatMessageToDto(saved);
    }

    /**
     * Cœur de la logique : trouve une convo active ou en crée une.
     */
    private ChatConversation findOrCreateConversation(SendMessageRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("user".equals(request.getSenderType())) {
            Optional<ChatConversation> existingConvo = chatConversationRepository
                    .findByUserIdAndStatusNot(request.getUserId(), "closed");

            if (existingConvo.isPresent()) {
                return existingConvo.get();
            } else {
                ChatConversation newConvo = new ChatConversation();
                newConvo.setUser(user);
                newConvo.setAgent(assignNewAgent());
                newConvo.setStatus("new");
                return chatConversationRepository.save(newConvo);
            }
        }

        else {
            Agent agent = agentRepository.findById(request.getAgentId())
                    .orElseThrow(() -> new RuntimeException("Agent not found"));

            return chatConversationRepository
                    .findByUserIdAndAgentIdAndStatusNot(request.getUserId(), request.getAgentId(), "closed")
                    .orElseThrow(() -> new RuntimeException("No active conversation found for this agent and user"));
        }
    }

    /**
     * Assignation simple d'un agent (pour un POC)
     */
    private Agent assignNewAgent() {
        return agentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No agents available"));
    }


    /**
     * Récupérer l'historique (Logique 2.0)
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getConversationHistory(Long userId) {
        Optional<ChatConversation> convo = chatConversationRepository.findByUserIdAndStatusNot(userId, "closed");

        if (convo.isEmpty()) {
            return List.of();
        }

        return chatMessageRepository.findByConversationIdOrderBySentAtAsc(convo.get().getId())
                .stream()
                .map(chatMessageMapper::chatMessageToDto)
                .collect(Collectors.toList());
    }

    /**
     * Polling (Logique 2.0) - PAR CONVERSATION ID
     */
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getNewMessages(Long conversationId, Long lastMessageId) {
        return chatMessageRepository.findNewMessages(conversationId, lastMessageId)
                .stream()
                .map(chatMessageMapper::chatMessageToDto)
                .collect(Collectors.toList());
    }

    /**
     * Dashboard de l'agent
     */
    @Transactional(readOnly = true)
    public List<ChatConversationResponseDTO> getConversationsForAgent(Long agentId) {
        return chatConversationRepository.findByAgentIdOrderByUpdatedAtDesc(agentId)
                .stream()
                .map(chatConversationMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * UTILE: Pour le simulateur de login
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * UTILE: Pour le simulateur de login
     */
    @Transactional(readOnly = true)
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
}