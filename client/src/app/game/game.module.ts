import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameComponent, ShardCardNotificationComponent } from './game/game.component';
import { PlayerListComponent } from './player-list/player-list.component';
import { GutterComponent} from './gutter/gutter.component';
import { MapComponent } from './map/map.component';
import { BankComponent } from './bank/bank.component';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialogModule } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatCardModule } from '@angular/material/card';
import { PlayerInfoDialogComponent } from './player-list/player-list.component';
import { ChatModule } from '../chat/chat.module';
import { DestCardComponent } from './dest-card/dest-card.component';
import {MatBadgeModule} from '@angular/material/badge';
import { SelectDestinationCardsDialogComponent } from './select-destination-cards-dialog/select-destination-cards-dialog.component';
import { ClaimRoutesDialogComponent } from './claim-routes-dialog/claim-routes-dialog.component';
import { RouteInfoDialogComponent } from './map/route-info-dialog/route-info-dialog.component';
import { GameOverDialogComponent } from './game-over-dialog/game-over-dialog.component';


@NgModule({
  declarations: [GameComponent, PlayerListComponent, GutterComponent, MapComponent, BankComponent, PlayerInfoDialogComponent, DestCardComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent, ShardCardNotificationComponent],
  entryComponents: [ PlayerInfoDialogComponent, SelectDestinationCardsDialogComponent, ClaimRoutesDialogComponent, RouteInfoDialogComponent, GameOverDialogComponent , ShardCardNotificationComponent],
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
