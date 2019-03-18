import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/core/model/player';
import { LobbyService } from '../lobby.service';
import { Color, StyleColor } from '@core/model/color.enum';
import { UserService } from '@core/user.service';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { LobbyState } from '@core/server/server-connection-state';

@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.scss']
})
export class GameInfoComponent implements OnInit {

  constructor(
    public lobbyService: LobbyService, 
    public userService: UserService,
    public serverConnection: ServerConnectionService) {}

  playerReady = false;

  ngOnInit() {
    // Change the server connection to lobby mode
    this.serverConnection.changeState(new LobbyState(this.serverConnection));
  }

  public getPlayerColorStyle(player: Player) {
    const style = {
      'background-color': '#' + StyleColor[player.getColor()]
    };
    return style;
  }

  public getColorStyle(color: Color) {
    const style = {
      'background-color': '#' + StyleColor[color]
    };
    return style;
  }

  public isUserReady(): boolean {
    this.playerReady = this.lobbyService.gameList.getSelectedGame().isPlayerReady(this.userService.user$.value.getUserId());
    return this.playerReady;
  }

  public onReady() {
    this.lobbyService.setReady(!this.playerReady);
  }

  public onLeave() {
    this.lobbyService.leaveGame();
  }

  public onSetColor(color: Color) {
    this.lobbyService.changeColor(color);
  }
}
