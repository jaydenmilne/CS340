import { Command } from './command';
import { GamePlayer } from './model/game-player';
import { DestinationCard, ShardCard } from './model/cards';


// Server commands
export class RequestDestinationsCommand implements Command {
    public command = "requestDestinations"

    constructor() {}
}

// Client Commands
export class UpdatePlayerCommand implements Command {
    public command = 'updatePlayer';
    public player : GamePlayer;

    constructor(updatePlayerCommand: any) {
        if (!("player" in updatePlayerCommand)) {
            throw new TypeError('Unable to deserialize updatePlayerCommand object, ' + JSON.stringify(updatePlayerCommand));
        }

        this.player = new GamePlayer(updatePlayerCommand.player);
    }
}

export class ChangeTurnCommand implements Command {
    public command = "changeTurn";
    public userId : Number;

    constructor(changeTurnCommand: any) {
        if (!("userId" in changeTurnCommand)) {
            throw new TypeError('Unable to deserialize changeTurnCommand object, ' + JSON.stringify(changeTurnCommand));
        }

        this.userId = changeTurnCommand.userId;
    }
}