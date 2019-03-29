import { ChangeTurnCommand } from '@core/game-commands';
import { PlayerService } from '../player.service';
import { TurnService } from './turn.service';
import { PlayerNotifierService } from '@core/player-notifier.service';

export abstract class ITurnState {
  constructor(
    protected playerService: PlayerService,
    protected turnService: TurnService,
    protected notifierService: PlayerNotifierService) {

    this.playerService = playerService;
    this.turnService = turnService;
  }

  abstract isMyTurn(): boolean;
  abstract canDrawShards(): boolean;
  abstract canDrawWild(): boolean;
  abstract canDrawDestinations(): boolean;
  abstract canClaimRoutes(): boolean;

  onChangeTurn(cmd: ChangeTurnCommand) {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }



  onClaimRoute() { }
  onDrawDeckShardCard() { }
  onDrawDestCard() { }
  onDrawFaceUpShardCard() { }
  onDrawFaceUpWildCard() { }
  onGameOver() {this.turnService.setNextState(new GameOverState(this.playerService, this.turnService, this.notifierService)); }


  enter() { }
  leave() { }
}

export class NotPlayersTurnState extends ITurnState {
  isMyTurn(): boolean {
    return false;
  }

  canDrawShards(): boolean {
    return false;
  }

  canDrawWild(): boolean {
    return false;
  }

  canDrawDestinations(): boolean {
    return false;
  }

  canClaimRoutes(): boolean {
    return false;
  }

  onChangeTurn(cmd: ChangeTurnCommand) {
    if (cmd.userId === this.playerService.myPlayerId) {
      this.turnService.setNextState(new PlayersTurnState(this.playerService, this.turnService, this.notifierService));
    }
  }

  enter() {
    this.notifierService.notifyPlayer('Your turn is over.');
  }
}

export class PlayersTurnState extends ITurnState {
  isMyTurn(): boolean {
    return true;
  }

  canDrawShards(): boolean {
    return true;
  }

  canDrawWild(): boolean {
    return true;
  }

  canDrawDestinations(): boolean {
    return true;
  }

  canClaimRoutes(): boolean {
    return true;
  }

  onClaimRoute() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }

  onDrawDeckShardCard() {
    this.turnService.setNextState(new DrawnFirstCardState(this.playerService, this.turnService, this.notifierService));
  }

  onDrawDestCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }

  onDrawFaceUpShardCard() {
    this.turnService.setNextState(new DrawnFirstCardState(this.playerService, this.turnService, this.notifierService));
  }

  onDrawFaceUpWildCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }

  enter() {
    this.notifierService.notifyPlayer('It is your turn!');
  }
}

export class DrawnFirstCardState extends ITurnState {
  isMyTurn(): boolean {
    return true;
  }

  canDrawShards(): boolean {
    return true;
  }

  canDrawWild(): boolean {
    return false;
  }

  canDrawDestinations(): boolean {
    return false;
  }

  canClaimRoutes(): boolean {
    return false;
  }

  enter() {
    this.notifierService.notifyPlayer('You may draw one more non-wild shard card');
  }

  onDrawDeckShardCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }

  onDrawFaceUpShardCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService, this.notifierService));
  }
}

export class GameOverState extends ITurnState {
  isMyTurn(): boolean {
    return false;
  }

  canDrawShards(): boolean {
    return false;
  }

  canDrawWild(): boolean {
    return false;
  }

  canDrawDestinations(): boolean {
    return false;
  }

  canClaimRoutes(): boolean {
    return false;
  }

  enter() {
    this.notifierService.notifyPlayer('The game is over');
  }

}
