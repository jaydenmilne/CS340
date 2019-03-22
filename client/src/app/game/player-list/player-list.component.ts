import { Component, OnInit, Inject } from '@angular/core';
import { StyleColor } from '@core/model/color.enum';
import { PlayerService } from '../player.service';
import { GamePlayer } from '@core/model/game-player';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-login-error-dialog',
  templateUrl: 'player-info-dialog.component.html',
  styleUrls: ['./player-info-dialog.component.scss']
})
export class PlayerInfoDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<PlayerInfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public player: GamePlayer) {}

  onOkClick(): void {
    this.dialogRef.close();
  }

}

@Component({
  selector: 'app-player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.scss']
})
export class PlayerListComponent implements OnInit {

  constructor(public playerService: PlayerService,
    public dialog: MatDialog) { }

  public getPlayerColorStyle(player: GamePlayer) {
    const style = {
      'border-color': '#' + StyleColor[player.color],
      'border-width': '4px',
      'border-style': 'solid',
      'background-color': 'transparent'
    };

    if (this.playerService.activePlayerId === player.userId) {
      // Highlight this player
      style['background-color'] = '#' + StyleColor[player.color];
    }

    return style;
  }

  public onPlayerClick(player: GamePlayer) {
    const dialogRef = this.dialog.open(PlayerInfoDialogComponent, {
      width: '343px',
      data: player
    });
  }

  public getPlayerRank(player: GamePlayer): String {
    const playersCopy = [... this.playerService.players];
    playersCopy.sort((p1, p2) => p2.points - p1.points);
    const rank = playersCopy.findIndex(p => p.userId === player.userId);

    switch (rank) {
      case 0:
      return '1st';
      case 1:
      return '2nd';
      case 2:
      return '3rd';
      case 3:
      return '4th';
      case 4:
      return '5th';
      default:
      return 'unranked';
    }
  }

  ngOnInit() {
  }

}
