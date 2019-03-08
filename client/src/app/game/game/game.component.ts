import { Component, OnInit } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';
import { MatDialog } from '@angular/material';
import { SelectDestinationCardsDialogComponent, SelectDestinationCardsData, SelectDestinationCardsResult } from '../select-destination-cards-dialog/select-destination-cards-dialog.component';
import { DestinationCard } from '@core/model/cards';
import { City } from '@core/model/route';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  constructor(private serverConnection: ServerConnectionService, public dialog: MatDialog) { }

  ngOnInit() {
    // Change the game connection to server mode
    this.serverConnection.changeState(new GameState(this.serverConnection));
  }

  public handleNewDestinationCards(newDestCards: DestinationCard[]){
    const dialogRef = this.dialog.open(SelectDestinationCardsDialogComponent, {
      width: '60%',
      data: new SelectDestinationCardsData(newDestCards, 2),    // TODO, this min required needs to be an argument
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(selectDestCardsResult => {
      console.log('The select cards dialog was closed');
      // TODO: Call card service
    });
  }
}
