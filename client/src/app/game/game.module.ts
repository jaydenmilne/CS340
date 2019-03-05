import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent} from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatGridListModule, MatChipsModule, MatDialogModule, MatButtonModule, MatTabsModule } from '@angular/material';
import { MatCardModule } from '@angular/material';
import { PlayerInfoDialogComponent } from './player-list/player-list.component'
import { ChatModule } from '../chat/chat.module';
import { DestCardComponent } from './dest-card/dest-card.component';


@NgModule({
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent, DestCardComponent],
  entryComponents: [ PlayerInfoDialogComponent ],
  imports: [
    CommonModule, 
    MatGridListModule,
    MatTabsModule,
    MatCardModule,
    MatChipsModule,
    MatDialogModule,
    MatButtonModule,
    ChatModule
  ]
})
export class GameModule { }
