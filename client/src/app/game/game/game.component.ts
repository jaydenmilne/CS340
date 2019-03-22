import { Component, OnInit } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';
import { MatDialog, MatSnackBar } from '@angular/material';
import { SelectDestinationCardsDialogComponent, SelectDestinationCardsData, SelectDestinationCardsResult } from '../select-destination-cards-dialog/select-destination-cards-dialog.component';
import { CardService } from '../card.service';
import { PlayerNotifierService } from '@core/player-notifier.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  constructor(private serverConnection: ServerConnectionService, public dialog: MatDialog, private cardService: CardService, private notifierService: PlayerNotifierService, private snackBar: MatSnackBar) { 
    this.cardService.stagedDestinationCards$.subscribe(result => this.handleNewDestinationCards(result));
    this.notifierService.playerNotification.subscribe(message => this.displayNotification(message));
  }

  ngOnInit() {
    // Change the game connection to server mode
    this.serverConnection.changeState(new GameState(this.serverConnection));
    this.cardService.requestDestinationCards();
  }

  public handleNewDestinationCards(newDestCardsData: SelectDestinationCardsData){
    const dialogRef = this.dialog.open(SelectDestinationCardsDialogComponent, {
      width: '60%',
      data: newDestCardsData,  
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(selectDestCardsResult => {
      console.log('The select cards dialog was closed');
      this.cardService.selectDestinationCards(selectDestCardsResult);
    });
  }

  public displayNotification(message: string){
    this.snackBar.open(message, '', {duration: 2500});
  }
}
