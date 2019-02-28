import { Component, OnInit } from '@angular/core';
import { StyleColor } from '@core/model/color.enum';
import { Player } from '@core/model/player';
import { PlayerService } from '../player.service';
import { GamePlayer } from '@core/model/game-player';

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.scss']
})
export class PlayerListComponent implements OnInit {

  constructor(private playerService: PlayerService) { }

  public getPlayerColorStyle(player: GamePlayer) {
    const style = {
      'border-color': '#' + StyleColor[player.getColor()],
      'border-width': '4px',
      'border-style': 'solid',
      'background-color': 'transparent'
    };

    if (this.playerService.activePlayerId == player.userId) {
      // Highlight this player
      style["background-color"] = '#' + StyleColor[player.getColor()];
    }

    return style;
  }

  ngOnInit() {
  }

}
