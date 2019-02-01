import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  gameList = []
  gameIndex = 0;
  selectedGame = "";

  onNewGame(){
    // Call new game on lobby service
    this.gameIndex++;
    this.gameList.push('game ' + String(this.gameIndex));
  }

  onGameSelect(game: string){
    this.selectedGame = game;
  }

}
