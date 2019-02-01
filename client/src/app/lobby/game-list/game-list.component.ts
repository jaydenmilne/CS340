import { Component, OnInit } from '@angular/core';
import { GameList } from 'src/app/core/model/GameList';
import { GamePreview } from 'src/app/core/model/GamePreview';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  constructor() { }

  gameList: GameList = new GameList([
    new GamePreview('game1', 'asldk', false, []),
    new GamePreview('game2', '531', false, []),
    new GamePreview('game3', 'vcb', false, []),
  ]);

  ngOnInit() {
  }

  onNewGame() {
    // Call new game on lobby service
  }

  onGameSelect(game: string) {
    this.gameList.setSelectedGameByID(game);
  }

}
