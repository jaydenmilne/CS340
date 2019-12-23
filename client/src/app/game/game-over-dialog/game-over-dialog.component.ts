import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PlayerPoint } from '@core/model/player-point';

export class GameOverViewData {
  constructor(public pointTotals: PlayerPoint[]) {}
}

@Component({
  selector: 'app-game-over-dialog',
  templateUrl: './game-over-dialog.component.html',
  styleUrls: ['./game-over-dialog.component.scss']
})
export class GameOverDialogComponent implements OnInit {
  dataSource: PlayerPoint[];
  displayedColumns: string[] = ['rank', 'player', 'routes', 'destinations', 'penalties', 'longestRoute', 'total'];
  constructor(
    public dialogRef: MatDialogRef<GameOverDialogComponent>,

    @Inject(MAT_DIALOG_DATA) public data: GameOverViewData) {
      this.dataSource = data.pointTotals;
      this.dataSource.sort((a, b) => b.totalPoints - a.totalPoints);
    }

  ngOnInit() {
  }

}

