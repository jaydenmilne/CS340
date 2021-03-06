import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login/login.component';
import { LobbyComponent } from './lobby/lobby/lobby.component';
import { GameComponent } from './game/game/game.component';

const routes: Routes = [
  // Login should be the first thing that happens
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'lobby',
    component: LobbyComponent
  },
  {
    path: 'lobby/game/:gameid',
    component: LobbyComponent
  },
  {
    path: 'game/:gameid',
    component: GameComponent,
  },
  // If navigating to /game without an id, kick them back to the lobby
  // so they pick one
  {
    path: 'game',
    pathMatch: 'full',
    redirectTo: 'lobby'
  },
];


@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
