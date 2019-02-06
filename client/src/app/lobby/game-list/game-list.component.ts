import { Component, OnInit } from '@angular/core';
import { LobbyService } from '../lobby.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { GamePreview } from '@core/model/game-preview';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  constructor(private lobbyService: LobbyService) { }

  showNamePrompt = false;

  createGameForm = new FormGroup({
    gameName: new FormControl('', [Validators.required, Validators.maxLength(20)])
  });

  ngOnInit() {
  }

  onNewGame() {
    this.showNamePrompt = true;
  }

  onCreateGame() {
    this.showNamePrompt = false;
    this.lobbyService.createGame(this.createGameForm.get('gameName').value);
  }

  onGameSelect(game: GamePreview) {
    this.lobbyService.joinGame(game);
  }

  onCancelNewGame() {
    this.showNamePrompt = false;
  }
}
