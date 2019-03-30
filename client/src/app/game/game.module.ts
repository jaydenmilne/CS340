import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent, ShardCardNotificationComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent} from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatGridListModule, MatChipsModule, MatDialogModule, MatButtonModule, MatTabsModule, MatSnackBarModule, MatTableModule, MatTooltipModule } from '@angular/material';
import { MatCardModule } from '@angular/material';
import { PlayerInfoDialogComponent } from './player-list/player-list.component';
import { ChatModule } from '../chat/chat.module';
import { DestCardComponent } from './dest-card/dest-card.component';
import {MatBadgeModule} from '@angular/material/badge';
import { SelectDestinationCardsDialogComponent } from './select-destination-cards-dialog/select-destination-cards-dialog.component';
import { ClaimRoutesDialogComponent } from './claim-routes-dialog/claim-routes-dialog.component';
import { RouteInfoDialogComponent } from './map/route-info-dialog/route-info-dialog.component';
import { GameOverDialogComponent } from './game-over-dialog/game-over-dialog.component';


@NgModule({
<<<<<<< HEAD
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent, DestCardComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent],
  entryComponents: [ PlayerInfoDialogComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent],
=======
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent, DestCardComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent, ShardCardNotificationComponent],
  entryComponents: [ PlayerInfoDialogComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent , ShardCardNotificationComponent],
>>>>>>> master
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
    MatTableModule,
    MatTooltipModule,
    ChatModule
  ]
})
export class GameModule { }
