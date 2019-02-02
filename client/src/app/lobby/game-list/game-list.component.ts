import { Component, OnInit } from '@angular/core';
import { GameList } from 'src/app/core/model/GameList';
import { GamePreview } from 'src/app/core/model/GamePreview';
import { LobbyService } from '../lobby.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  constructor(private lobbyService: LobbyService) { }

  ngOnInit() {
  }

  onNewGame() {
    // Get game name

    // Call new game on lobby service
    this.lobbyService.createGame('');
  }

  onGameSelect(game: string) {
    this.lobbyService.gameList.setSelectedGameByID(game);
  }

}
