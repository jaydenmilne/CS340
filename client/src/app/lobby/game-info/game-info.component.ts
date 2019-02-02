import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/core/model/Player';
import { LobbyService } from '../lobby.service';

@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.scss']
})
export class GameInfoComponent implements OnInit {

  constructor(private lobbyService: LobbyService) { }

  playerReady = false;

  ngOnInit() {
  }

  public getPlayerColorStyle(player: Player) {
    const style = {
      'background-color': '#' + player.getColor()
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
}
