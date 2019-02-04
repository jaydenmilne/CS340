import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LobbyComponent } from './lobby/lobby.component';
import { GameListComponent } from './game-list/game-list.component';
import { GameInfoComponent } from './game-info/game-info.component';
import { MatCardModule, MatButtonModule, MatChipsModule, MatInputModule, MatFormFieldModule, MatIconModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LobbyService } from './lobby.service';


@NgModule({
  declarations: [LobbyComponent, GameListComponent, GameInfoComponent],
  providers: [LobbyService],
  imports: [
    CommonModule, MatCardModule, MatButtonModule, MatChipsModule, MatInputModule, MatFormFieldModule, FormsModule, ReactiveFormsModule, MatIconModule
  ]
})
export class LobbyModule { }
