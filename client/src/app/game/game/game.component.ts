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

    const dialogRef = this.dialog.open(SelectDestinationCardsDialogComponent, {
      width: '60%',
      data: new SelectDestinationCardsData([
        new DestinationCard({'cities':[City.XANDAR, City.ASGARD], 'points': 5}),
        new DestinationCard({'cities':[City.CHITAURI_SANCTUARY, City.HONG_KONG_SANCTUM], 'points': 10}),
        new DestinationCard({'cities':[City.GALACTUS, City.YOTUNHEIM], 'points': 15}),
      ], 2),
      disableClose: true
    });
    let selectedCards: SelectDestinationCardsResult;
    dialogRef.afterClosed().subscribe(result => {
      console.log('The select cards dialog was closed');
      selectedCards = result;
      // Call Card Service
    });
  }

}
