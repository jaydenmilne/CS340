import { Injectable } from '@angular/core';
import { ITurnState, NotPlayersTurnState } from './turn-states';
import { PlayerService } from '../player.service';
import { ChangeTurnCommand } from '@core/game-commands';
import { CommandRouterService } from '@core/command-router.service';

@Injectable({
  providedIn: 'root'
})
export class TurnService {
  private state: ITurnState;
  private router: CommandRouterService;

  constructor(playerService: PlayerService, router: CommandRouterService) {
    this.state = new NotPlayersTurnState(playerService, this);
    this.router = router;

    this.router.changeTurn$.subscribe(cmd => this.onChangeTurn(cmd));
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
    this.state = state;
  }

  onChangeTurn(cmd: ChangeTurnCommand) {
    this.state.onChangeTurn(cmd);
  };

  onClaimRoute() {
    this.state.onClaimRoute();
  };

  onDrawDeckShardCard() {
    this.state.onDrawDeckShardCard();
  };

  onDrawDestCard() {
    this.state.onDrawDestCard();
  };

  onDrawFaceUpShardCard() {
    this.state.onDrawFaceUpShardCard();
  };

  onDrawFaceUpWildCard() {
    this.state.onClaimRoute();
  };

}
