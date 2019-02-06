import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/core/model/player';
import { LobbyService } from '../lobby.service';
import { Color } from '@core/model/color.enum';
import { PollerService } from '@core/poller.service';

@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.scss']
})
export class GameInfoComponent implements OnInit {

  constructor(private lobbyService: LobbyService, private poller: PollerService) { 
    this.poller.startPolling();
  }

  playerReady = false;

  ngOnInit() {
  }

  public getPlayerColorStyle(player: Player) {
    const style = {
      'background-color': '#' + player.getColor()
    };
    return style;
  }

  public getColorStyle(color: Color) {
    const style = {
      'background-color': '#' + color
    };
    return style;
  }

  public onReady() {
    this.playerReady = !this.playerReady;
    this.lobbyService.setReady(this.playerReady);
  }

  public onLeave() {
    this.lobbyService.leaveGame();
  }

  public onSetColor(color: Color) {
    this.lobbyService.changeColor(color);
  }
}
