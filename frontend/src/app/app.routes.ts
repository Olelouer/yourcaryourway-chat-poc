import { Routes } from '@angular/router';
import { LoginSimulatorComponent } from './views/login-simulator/login-simulator.component';
import { ClientChatComponent } from './views/client-chat/client-chat.component';
import { AgentDashboardComponent } from './views/agent-dashboard/agent-dashboard.component';

export const routes: Routes = [
    { path: 'login', component: LoginSimulatorComponent },

    { path: 'client/:id', component: ClientChatComponent },

    { path: 'agent/:id', component: AgentDashboardComponent },

    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: '**', redirectTo: '/login' }
];