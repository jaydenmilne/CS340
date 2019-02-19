import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/core/model/player';
import { LobbyService } from '../lobby.service';
import { Color, StyleColor } from '@core/model/color.enum';
import { PollerService } from '@core/poller.service';
import { UserService } from '@core/user.service';

@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.scss']
})
export class GameInfoComponent implements OnInit {

  constructor(private lobbyService: LobbyService, private poller: PollerService, private userService: UserService) {
    this.poller.setLobbyMode(true);
    this.poller.startPolling();
  }

  playerReady = false;

  ngOnInit() {
  }

  public ngOnDestroy(){
    this.poller.stopPolling();
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

  public isUserReady(): boolean{
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
