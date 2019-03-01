import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent } from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatGridListModule, MatChipsModule, MatDialogModule, MatButtonModule } from '@angular/material';
import { MatCardModule } from '@angular/material';
import { PlayerInfoDialogComponent } from './player-list/player-list.component'
@NgModule({
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent],
  entryComponents: [ PlayerInfoDialogComponent ],
  imports: [
    CommonModule, 
    MatGridListModule,
    MatCardModule,
    MatChipsModule,
    MatDialogModule,
    MatButtonModule
  ]
})
export class GameModule { }
