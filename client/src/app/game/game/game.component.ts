import { Component, OnInit } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  constructor(private serverConnection: ServerConnectionService) { }

  ngOnInit() {
    // Change the game connection to server mode
    this.serverConnection.changeState(new GameState(this.serverConnection));
  }

}
