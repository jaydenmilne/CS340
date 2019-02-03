import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LobbyComponent } from './lobby/lobby.component';
import { GameListComponent } from './game-list/game-list.component';
import { GameInfoComponent } from './game-info/game-info.component';
import { ChatModule } from '../chat/chat.module';
import { UserService } from '@core/user.service';

@NgModule({
  declarations: [LobbyComponent, GameListComponent, GameInfoComponent],
  imports: [
    CommonModule, ChatModule
  ],
  providers: [UserService]
})
export class LobbyModule { }
