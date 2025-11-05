import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { ChatMessageResponse } from '../interfaces/models.dto';
import { SendMessageRequest } from '../interfaces/models.dto';
import { User } from '../interfaces/models.dto';
import { Agent } from '../interfaces/models.dto';

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

    getNewMessages(userId: number, lastMessageId: number): Observable<ChatMessageResponse[]> {
        return this.http.get<ChatMessageResponse[]>(`${this.apiUrl}/messages/new/${userId}?lastMessageId=${lastMessageId}`);
    }

    // Envoyer un message
    sendMessage(request: SendMessageRequest): Observable<ChatMessageResponse> {
        return this.http.post<ChatMessageResponse>(`${this.apiUrl}/messages`, request);
    }
}