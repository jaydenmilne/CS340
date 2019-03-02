import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent } from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatGridListModule } from '@angular/material';
import { ChatModule } from '../chat/chat.module';

@NgModule({
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent],
  imports: [
    CommonModule, MatGridListModule, ChatModule
  ]
})
export class GameModule { }
