import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LobbyComponent } from './lobby/lobby.component';
import { GameListComponent } from './game-list/game-list.component';
import { GameInfoComponent } from './game-info/game-info.component';
import { MatCardModule, MatButtonModule, MatChipsModule } from '@angular/material';
import { LobbyService } from './lobby.service';


@NgModule({
  declarations: [LobbyComponent, GameListComponent, GameInfoComponent],
  providers: [LobbyService],
  imports: [
    CommonModule, MatCardModule, MatButtonModule, MatChipsModule
  ]
})
export class LobbyModule { }
