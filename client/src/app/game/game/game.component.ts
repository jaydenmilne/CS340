import { Component, OnInit } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';
import { MatDialog } from '@angular/material';
import { SelectDestinationCardsDialogComponent } from '../select-destination-cards-dialog/select-destination-cards-dialog.component';
import { DestinationCard, City } from '@core/model/cards';

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
      data: {'newCards':[  
      new DestinationCard([City.XANDAR, City.ASGARD], 5),
      new DestinationCard([City.CHITAURI_SANCTUARY, City.HONG_KONG_SANCTUM], 10),
      new DestinationCard([City.GALACTUS, City.YOTUNHEIM], 15),],
      'minRequired': 2 },
      disableClose: true
    });
  }

}
