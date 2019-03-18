import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent} from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatGridListModule, MatChipsModule, MatDialogModule, MatButtonModule, MatTabsModule, MatSnackBarModule } from '@angular/material';
import { MatCardModule } from '@angular/material';
import { PlayerInfoDialogComponent } from './player-list/player-list.component'
import { ChatModule } from '../chat/chat.module';
import { DestCardComponent } from './dest-card/dest-card.component';
import {MatBadgeModule} from '@angular/material/badge';
import { SelectDestinationCardsDialogComponent } from './select-destination-cards-dialog/select-destination-cards-dialog.component';
import { ClaimRoutesDialogComponent } from './claim-routes-dialog/claim-routes-dialog.component';


@NgModule({
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent, DestCardComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent],
  entryComponents: [ PlayerInfoDialogComponent, SelectDestinationCardsDialogComponent ],
  imports: [
    CommonModule, 
    MatGridListModule,
    MatTabsModule,
    MatCardModule,
    MatChipsModule,
    MatSnackBarModule,
    MatDialogModule,
    MatButtonModule,
    MatBadgeModule,
    ChatModule
  ]
})
export class GameModule { }
