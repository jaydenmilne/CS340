import { Injectable } from '@angular/core';
import { ITurnState, NotPlayersTurnState } from './turn-states';
import { PlayerService } from '../player.service';
import { ChangeTurnCommand, SkipTurnCommand } from '@core/game-commands';
import { CommandRouterService } from '@core/command-router.service';
import { PlayerNotifierService } from '@core/player-notifier.service';
import { ServerProxyService } from '@core/server/server-proxy.service';

@Injectable({
  providedIn: 'root'
})
export class TurnService {
  private state: ITurnState;
  private router: CommandRouterService;
  public wasPlayersTurn: Boolean = false;

  constructor(
    private serverProxy: ServerProxyService,
    private playerService: PlayerService,
    router: CommandRouterService,
    private playerNotifier: PlayerNotifierService) {
    this.state = new NotPlayersTurnState(playerService, this, playerNotifier);
    this.router = router;

    this.router.changeTurn$.subscribe(cmd => this.onChangeTurn(cmd));
    this.router.gameOver$.subscribe(cmd => this.onGameOver());
  }

  public skipTurn() {
    this.serverProxy.executeCommand(new SkipTurnCommand());
  }

  isMyTurn(): boolean {
    return this.state.isMyTurn();
  }

  canDrawShards(): boolean {
    return this.state.canDrawShards();
  }

  canDrawWild(): boolean {
    return this.state.canDrawWild();
  }

  canDrawDestinations(): boolean {
    return this.state.canDrawDestinations();
  }

  canClaimRoutes(): boolean {
    return this.state.canClaimRoutes();
  }

  setNextState(state: ITurnState) {
    this.state.leave();
    this.state = state;
    this.state.enter();
  }

  onChangeTurn(cmd: ChangeTurnCommand) {
    if (this.wasPlayersTurn) {
      this.playerNotifier.notifyPlayer('Your turn is over.');
      this.wasPlayersTurn = false;
    }
    else if (this.playerService.myPlayerId == this.playerService.activePlayerId) {
      this.playerNotifier.notifyPlayer('It is your turn!');
      this.wasPlayersTurn = true;
    }
    this.state.onChangeTurn(cmd);
  }

  onClaimRoute() {
    this.state.onClaimRoute();
  }

  onDrawDeckShardCard() {
    this.state.onDrawDeckShardCard();
  }

  onDrawDestCard() {
    this.state.onDrawDestCard();
  }

  onDrawFaceUpShardCard() {
    this.state.onDrawFaceUpShardCard();
  }

  onDrawFaceUpWildCard() {
    this.state.onClaimRoute();
  }

  onGameOver() {
    this.state.onGameOver();
  }

}
