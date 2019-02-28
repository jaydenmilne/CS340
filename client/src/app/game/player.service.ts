import { Injectable } from '@angular/core';
import { CommandRouterService } from '@core/command-router.service';
import { Color } from '@core/model/color.enum';
import { UpdatePlayerCommand, ChangeTurnCommand } from '@core/game-commands.ts';
import { GamePlayer } from '@core/model/game-player';
import { notEqual } from 'assert';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  // Sorted by turn order
  public players : GamePlayer[] = [];
  public playersById : Map<Number, GamePlayer> = new Map();
  public activePlayerId : Number = null;

  private onUpdatePlayer(updatePlayerCommand : UpdatePlayerCommand) {
    let gamePlayer = updatePlayerCommand.player;

    // Check if this is a new player or an updated one
    if (this.playersById.has(gamePlayer.userId)) {
      // Already exists, update
      
      let idx = this.players.findIndex(player => player.userId == gamePlayer.userId)
      notEqual(idx, -1, "Player exists but doesn't?");
      
      // Replace the old gamePlayer with the new
      this.players[idx] = gamePlayer;

      // Update the map
      this.playersById.set(gamePlayer.userId, gamePlayer);

    } else {
      // New player
      this.players.push(gamePlayer);
      // Resort the players by turn order
      this.players.sort((p1, p2) => p1.turnOrder - p2.turnOrder);
      
      // Add to the map
      this.playersById.set(gamePlayer.userId, gamePlayer);
    }
  }

  private onTurnChange(changeTurnCommand : ChangeTurnCommand) {
    this.activePlayerId = changeTurnCommand.userId;
  }

  constructor(private commandRouter : CommandRouterService) {
    commandRouter.updatePlayer$.subscribe(updatePlayerCommand => this.onUpdatePlayer(updatePlayerCommand));
    commandRouter.changeTurn$.subscribe(changeTurnCommand => this.onTurnChange(changeTurnCommand));

    // DUMMY DATA

    let p1 = {
      "color": <Color> Color["RED"],
      "userId": 1,
      "ready": true,
      "username": "hotstuff32",
      "points": -1,
      "numTrainCards": 1,
      "numDestinationCards": 2,
      "numRemainingTrains": 3,
      "hasLongestRoute": false,
      "turnOrder": 1
    };

  let p2 = {
    "color": <Color> Color["BLUE"],
    "userId": 2,
    "ready": true,
    "username": "2dangerous",
    "points": -2,
    "numTrainCards": 2,
    "numDestinationCards": 2,
    "numRemainingTrains": 3,
    "hasLongestRoute": false,
    "turnOrder": 2
  };
  let c1 = new UpdatePlayerCommand({"player": p1});
  let c2 = new UpdatePlayerCommand({"player": p2});
  
  this.activePlayerId = 1;

  this.onUpdatePlayer(c1);
  this.onUpdatePlayer(c2);

  }
}
