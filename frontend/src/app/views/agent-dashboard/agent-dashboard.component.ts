import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../../services/chat.service';
import { User } from '../../interfaces/models.dto';
import { ChatMessageResponse } from '../../interfaces/models.dto';
import { SendMessageRequest } from '../../interfaces/models.dto';

@Component({
  selector: 'app-agent-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agent-dashboard.component.html',
})
export class AgentDashboardComponent implements OnInit, OnDestroy {

  users: User[] = [];
  selectedUser: User | null = null;
  messages: ChatMessageResponse[] = [];
  newMessageText: string = '';
  currentAgentId: number = 0;

  private lastMessageId: number = 0;
  private pollingInterval: any;

  private chatService = inject(ChatService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.currentAgentId = +id;
      }
    });

    this.chatService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

  ngOnDestroy(): void {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }

  selectUser(user: User): void {
    this.selectedUser = user;
    this.messages = [];
    this.lastMessageId = 0;

    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }

    this.chatService.getHistory(user.id).subscribe(history => {
      this.messages = history;
      this.updateLastMessageId();
      this.startPolling(user.id);
    });
  }

  startPolling(userId: number): void {
    this.pollingInterval = setInterval(() => {
      this.chatService.getNewMessages(userId, this.lastMessageId).subscribe(newMessages => {
        if (newMessages.length > 0) {
          this.messages = [...this.messages, ...newMessages];
          this.updateLastMessageId();
        }
      });
    }, 2000);
  }

  sendMessage(): void {
    if (!this.newMessageText.trim() || !this.selectedUser || this.currentAgentId === 0) return;

    const request: SendMessageRequest = {
      userId: this.selectedUser.id,
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