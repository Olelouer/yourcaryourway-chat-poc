import { Component, OnInit, signal } from '@angular/core';

import { Router } from '@angular/router';
import { ChatService } from '../../services/chat.service';
import { User } from '../../interfaces/models.dto';
import { Agent } from '../../interfaces/models.dto';

@Component({
  selector: 'app-login-simulator',
  imports: [],
  templateUrl: './login-simulator.component.html',
})
export class LoginSimulatorComponent implements OnInit {

  users = signal<User[]>([]);
  agents = signal<Agent[]>([]);

  constructor(private chatService: ChatService, private router: Router) { }

  ngOnInit(): void {
    this.chatService.getAllUsers().subscribe(data => this.users.set(data));
    this.chatService.getAllAgents().subscribe(data => this.agents.set(data));
  }

  loginAsUser(userId: number): void {
    this.router.navigate(['/client', userId]);
  }

  loginAsAgent(agentId: number): void {
    this.router.navigate(['/agent', agentId]);
  }
}