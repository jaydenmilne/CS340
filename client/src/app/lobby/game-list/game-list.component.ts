import { Component, OnInit } from '@angular/core';
import { GameList } from 'src/app/Model/GameList';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  gameList : GameList = new GameList(['game1', 'game2', 'game3']);
  selectedGame = "";

  onNewGame(){
    // Call new game on lobby service
  }

  onGameSelect(game: string){
    this.gameList.setSelectedGame(game);
  }

}
