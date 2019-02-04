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

    constructor( public gameId: string) {}
}

export class ChangeColorCommand implements Command {
    public command = 'changeColor';

    constructor( public gameId: string, public newColor: Color) {}
}

export class LeaveGameCommand implements Command {
    public command = 'leaveGame';

    constructor( public gameId: string) {}
}

export class CreateGameCommand implements Command {
    public command = 'createGame';

    constructor( public name: string) {}
}

export class PlayerReadyCommand implements Command {
    public command = 'playerReady';

    constructor( public gameId: string,
        public playerIsReady: boolean) {}
}

// Client commands
export class GameCreatedCommand implements Command {
    public command = 'gameCreated';

    constructor( public gameId: string) {}
}

export class StartGameCommand implements Command {
    public command = 'startGame';

    constructor( public gameId: string ) {}
}

export class RefreshGameListCommand implements Command {
    public command = 'startGame';

    constructor( public games: GamePreview ) {}
}
