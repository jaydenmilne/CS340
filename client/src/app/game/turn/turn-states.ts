import { ChangeTurnCommand } from '@core/game-commands';
import { PlayerService } from '../player.service';
import { ShardCard } from '@core/model/cards';
import { TurnService } from './turn.service';

export abstract class ITurnState {
  protected playerService: PlayerService;
  protected turnService: TurnService;

  constructor(playerService: PlayerService, turnService: TurnService) {
    this.playerService = playerService;
    this.turnService = turnService;
  }

  abstract isMyTurn(): boolean;
  abstract canDrawShards(): boolean;
  abstract canDrawWild(): boolean;
  abstract canDrawDestinations(): boolean;
  abstract canClaimRoutes(): boolean;

  onChangeTurn(cmd: ChangeTurnCommand) { };
  onClaimRoute() { };
  onDrawDeckShardCard() { };
  onDrawDestCard() { };
  onDrawFaceUpShardCard() { };
  onDrawFaceUpWildCard() { };

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
      this.turnService.setNextState(new PlayersTurnState(this.playerService, this.turnService))
    }
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
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService));
  };

  onDrawDeckShardCard() {
    this.turnService.setNextState(new DrawnFirstCardState(this.playerService, this.turnService));
  };

  onDrawDestCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService));
  };

  onDrawFaceUpShardCard() {
    this.turnService.setNextState(new DrawnFirstCardState(this.playerService, this.turnService));
  };

  onDrawFaceUpWildCard() {
    this.turnService.setNextState(new NotPlayersTurnState(this.playerService, this.turnService));
  };
}

export class DrawnFirstCardState extends ITurnState {
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
    return false;
  }

  canClaimRoutes(): boolean {
    return false;
  }
}

export class GameOverState extends ITurnState {
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
    return false;
  }

  canClaimRoutes(): boolean {
    return false;
  }
}