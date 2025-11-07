import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ChatMessageResponse } from '../interfaces/models.dto';
import { SendMessageRequest } from '../interfaces/models.dto';
import { User } from '../interfaces/models.dto';
import { Agent } from '../interfaces/models.dto';
import { ChatConversationResponseDTO } from '../interfaces/models.dto';

@Injectable({
    providedIn: 'root'
})
export class ChatService {
    private apiUrl = 'http://localhost:8080/api/chat';
    private http = inject(HttpClient);

    getAllUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${this.apiUrl}/users`);
    }

    getAllAgents(): Observable<Agent[]> {
        return this.http.get<Agent[]>(`${this.apiUrl}/agents`);
    }

    getHistory(userId: number): Observable<ChatMessageResponse[]> {
        return this.http.get<ChatMessageResponse[]>(`${this.apiUrl}/history/${userId}`);
    }

    sendMessage(request: SendMessageRequest): Observable<ChatMessageResponse> {
        return this.http.post<ChatMessageResponse>(`${this.apiUrl}/messages`, request);
    }

    getNewMessages(conversationId: number, lastMessageId: number): Observable<ChatMessageResponse[]> {
        const url = `${this.apiUrl}/messages/new/conversation/${conversationId}?lastMessageId=${lastMessageId}`;
        return this.http.get<ChatMessageResponse[]>(url);
    }

    getAgentConversations(agentId: number): Observable<ChatConversationResponseDTO[]> {
        return this.http.get<ChatConversationResponseDTO[]>(`${this.apiUrl}/agent/conversations/${agentId}`);
    }
}