import { Command } from './command';
import { Color } from '@core/model/color.enum';
import { GamePreview } from './model/game-preview';


// Server commands
export class ListGamesCommand implements Command {
    public command = 'listGames';

    constructor() {}
}

export class JoinGameCommand implements Command {
    public command = 'joinGame';

    constructor( public gameId: number) {}
}

export class ChangeColorCommand implements Command {
    public command = 'changeColor';

    constructor( public gameId: number, public newColor: Color) {}
}

export class LeaveGameCommand implements Command {
    public command = 'leaveGame';

    constructor( public gameId: number) {}
}

export class CreateGameCommand implements Command {
    public command = 'createGame';

    constructor( public name: string) {}
}

export class PlayerReadyCommand implements Command {
    public command = 'playerReady';

    constructor( public gameId: number,
        public playerIsReady: boolean) {}
}

// Client commands
export class GameCreatedCommand implements Command {
    public command = 'gameCreated';
public gameId: number;

    constructor(gameCreatedCommand: any) {
        if (!('gameId' in gameCreatedCommand)) {
                throw new TypeError('Unable to deserialize GameCreatedCommand object, ' + JSON.stringify(gameCreatedCommand));
        }
        this.gameId = gameCreatedCommand.gameId;

    }
}

export class StartGameCommand implements Command {
    public command = 'startGame';
    public gameId: string;

    constructor(startGameCommand: any) {
        if (!('gameId' in startGameCommand)) {
                throw new TypeError('Unable to deserialize StartGameCommand object, ' + JSON.stringify(startGameCommand));
        }
        this.gameId = startGameCommand.gameId;

    }
}

export class RefreshGameListCommand implements Command {
    public command = 'refreshGameList';
    public games: GamePreview [];

    constructor(refreshGameListCommand: any) {
        if (!('games' in refreshGameListCommand)) {
                throw new TypeError('Unable to deserialize RefreshGameListCommand object, ' + JSON.stringify(refreshGameListCommand));
        }
        // TODO: Figure out how to deserialize Game Previews.
        this.games = [];
        refreshGameListCommand.games.forEach(game => {
            this.games.push(new GamePreview(game));
        });
    }
}
