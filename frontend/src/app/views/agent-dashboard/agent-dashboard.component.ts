import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat.service';
import { ChatConversationResponseDTO } from '../../interfaces/models.dto';
import { ChatMessageResponse } from '../../interfaces/models.dto';
import { SendMessageRequest } from '../../interfaces/models.dto';

@Component({
  selector: 'app-agent-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent-dashboard.component.html',
})
export class AgentDashboardComponent implements OnInit, OnDestroy {

  conversations: ChatConversationResponseDTO[] = [];
  selectedConversation: ChatConversationResponseDTO | null = null;

  messages: ChatMessageResponse[] = [];
  newMessageText: string = '';
  currentAgentId: number = 0;

  private currentConversationId: number = 0;
  private lastMessageId: number = 0;
  private pollingInterval: any;

  private chatService = inject(ChatService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.currentAgentId = +id;
        this.loadAgentDashboard();
      }
    });
  }

  loadAgentDashboard(): void {
    this.chatService.getAgentConversations(this.currentAgentId).subscribe(convs => {
      this.conversations = convs;
    });
  }

  ngOnDestroy(): void {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }

  selectConversation(conv: ChatConversationResponseDTO): void {
    this.selectedConversation = conv;
    this.messages = [];
    this.lastMessageId = 0;
    this.currentConversationId = conv.conversationId;

    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }

    this.chatService.getHistory(conv.userId).subscribe(history => {
      this.messages = history;
      this.updateLastMessageId();
      this.startPolling(conv.conversationId);
    });
  }

  startPolling(conversationId: number): void {
    if (conversationId === 0) return;

    this.pollingInterval = setInterval(() => {
      this.chatService.getNewMessages(conversationId, this.lastMessageId).subscribe(newMessages => {
        if (newMessages.length > 0) {
          this.messages = [...this.messages, ...newMessages];
          this.updateLastMessageId();
        }
      });
    }, 2000);
  }

  sendMessage(): void {
    if (!this.newMessageText.trim() || !this.selectedConversation || this.currentAgentId === 0) return;

    const request: SendMessageRequest = {
      userId: this.selectedConversation.userId,
      agentId: this.currentAgentId,
      message: this.newMessageText,
      senderType: 'agent'
    };

    this.chatService.sendMessage(request).subscribe();
    this.newMessageText = '';
  }

  private updateLastMessageId(): void {
    if (this.messages.length > 0) {
      this.lastMessageId = this.messages[this.messages.length - 1].id;
    }
  }
}