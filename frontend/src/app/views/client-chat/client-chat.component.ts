import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat.service';
import { ChatMessageResponse } from '../../interfaces/models.dto';
import { SendMessageRequest } from '../../interfaces/models.dto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-client-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-chat.component.html',
})
export class ClientChatComponent implements OnInit, OnDestroy {

  messages: ChatMessageResponse[] = [];
  newMessageText: string = '';
  currentUserId: number = 0;

  private currentConversationId: number = 0;
  private lastMessageId: number = 0;
  private pollingInterval: any;

  private chatService = inject(ChatService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.currentUserId = +id;
        this.cleanup();
        this.loadHistory();
      }
    });
  }

  ngOnDestroy(): void {
    this.cleanup();
  }

  private cleanup(): void {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
    this.messages = [];
    this.lastMessageId = 0;
    this.currentConversationId = 0;
  }

  loadHistory(): void {
    this.chatService.getHistory(this.currentUserId).subscribe(history => {
      this.messages = history;
      this.updateLastMessageId();

      if (history.length > 0) {
        this.currentConversationId = history[0].conversationId;
        this.startPolling();
      }
    });
  }

  startPolling(): void {
    if (this.currentConversationId === 0) return;

    this.pollingInterval = setInterval(() => {
      this.chatService.getNewMessages(this.currentConversationId, this.lastMessageId).subscribe(newMessages => {
        if (newMessages.length > 0) {
          this.messages = [...this.messages, ...newMessages];
          this.updateLastMessageId();
        }
      });
    }, 2000);
  }

  sendMessage(): void {
    if (!this.newMessageText.trim() || this.currentUserId === 0) return;

    const request: SendMessageRequest = {
      userId: this.currentUserId,
      agentId: null,
      message: this.newMessageText,
      senderType: 'user'
    };

    this.chatService.sendMessage(request).subscribe(sentMessage => {
      if (this.currentConversationId === 0) {
        this.currentConversationId = sentMessage.conversationId;
        this.startPolling();
      }
    });
    this.newMessageText = '';
  }

  private updateLastMessageId(): void {
    if (this.messages.length > 0) {
      this.lastMessageId = this.messages[this.messages.length - 1].id;
    }
  }
}