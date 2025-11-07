export interface User {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
}

export interface Agent {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
}

export interface ChatMessageResponse {
    id: number;
    conversationId: number;
    message: string;
    senderType: 'user' | 'agent';
    senderName: string;
    senderId: number;
    sentAt: string;
}

export interface SendMessageRequest {
    userId: number;
    agentId: number | null;
    message: string;
    senderType: 'user' | 'agent';
}

export interface ChatConversationResponseDTO {
    conversationId: number;
    status: string;
    lastMessageTime: string;
    userId: number;
    userFirstName: string;
    userLastName: string;
}