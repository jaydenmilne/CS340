import { Injectable } from '@angular/core';
import { CommandRouterService } from '@core/command-router.service';
import { Color, StyleColor } from '@core/model/color.enum';
import { UpdatePlayerCommand, ChangeTurnCommand, GameOverCommand, LastRoundCommand } from '@core/game-commands';
import { GamePlayer } from '@core/model/game-player';
import { notEqual } from 'assert';
import { UserService } from '@core/user.service';
import { GameOverViewData } from './game-over-dialog/game-over-dialog.component';
import { Subject } from 'rxjs';
import { PlayerPoint } from '@core/model/player-point';
import { PlayerNotifierService } from '@core/player-notifier.service';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  // Sorted by turn order
  public players: GamePlayer[] = [];
  public playersById: Map<Number, GamePlayer> = new Map();
  public activePlayerId: Number = null;
  public myPlayerId = 0;
  public myPlayer: GamePlayer = null;
  public playerPointTotals$ = new Subject<GameOverViewData>();
  

  private onUpdatePlayer(updatePlayerCommand: UpdatePlayerCommand) {
    const gamePlayer = updatePlayerCommand.gamePlayer;

    // Check if this is a new player or an updated one
    if (this.playersById.has(gamePlayer.userId)) {
      // Already exists, update

      const idx = this.players.findIndex(player => player.userId === gamePlayer.userId);
      notEqual(idx, -1, 'Player exists but doesn\'t?');

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

    this.players.sort((l, r): number => {
      if (l.turnOrder < r.turnOrder) {return -1; }
      if (l.turnOrder > r.turnOrder) {return 1; }
      return 0;
    });

    this.myPlayer = this.playersById.get(this.myPlayerId);
  }

  private onTurnChange(changeTurnCommand: ChangeTurnCommand) {
    this.activePlayerId = changeTurnCommand.userId;
  }

  private onGameOver(gameOverCommand: GameOverCommand) {
    this.playerPointTotals$.next(new GameOverViewData(gameOverCommand.players));
  }

  private onLastRound(){
    this.playerNotifier.notifyPlayer("Last Round Has Begun!")
  }

  getMyPlayerColor(): StyleColor{
    if (this.myPlayer === undefined || this.myPlayer === null) {
      return StyleColor.YELLOW;
    }

    return this.myPlayer.getStyleColor();
  }

  getPlayerCSSColor(playerId: number, attribute: string) {
    let player: GamePlayer = this.playersById.get(playerId)
    if (player === undefined || player === null) {
      return {[attribute]: '#' + StyleColor.YELLOW};
    }

    return player.getCSSColor(attribute);
  }

  getMyPlayerCSSColor(attribute: string) {
    if (this.myPlayer === undefined || this.myPlayer === null) {
      return {[attribute]: '#' + StyleColor.YELLOW};
    }

    return this.myPlayer.getCSSColor(attribute);
  }

  constructor(private commandRouter: CommandRouterService, private userService: UserService,
    private playerNotifier: PlayerNotifierService) {
    userService.user$.subscribe(user => {
      this.myPlayerId = (user === null || user === undefined) ? 0 : user.getUserId();
    });

    commandRouter.updatePlayer$.subscribe(updatePlayerCommand => this.onUpdatePlayer(updatePlayerCommand));
    commandRouter.changeTurn$.subscribe(changeTurnCommand => this.onTurnChange(changeTurnCommand));
    commandRouter.gameOver$.subscribe(gameOverCommand => this.onGameOver(gameOverCommand));
    commandRouter.lastRound$.subscribe(lastRoundCommand => this.onLastRound());
  }
  
  public clearData(){
    this.players = [];
    this.playersById = new Map();
    this.activePlayerId = null;
    // this.myPlayerId = 0;
    this.myPlayer = null;
  }

}
