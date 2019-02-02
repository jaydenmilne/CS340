import { Command } from './command';
import { GameList } from './model/GameList';
import { Color } from '../Model/color.enum';


// Server commands
export class ListGamesCommand implements Command {
    public command = 'listGames';

    constructor() {}
}

export class JoinGameCommand implements Command {
    public command = 'joinGame';

    constructor( public gameID: string) {}
}

export class ChangeColorCommand implements Command {
    public command = 'changeColor';

    constructor( public gameID: string, public newColor: Color) {}
}

export class LeaveGameCommand implements Command {
    public command = 'leaveGame';

    constructor( public gameID: string) {}
}

export class CreateGameCommand implements Command {
    public command = 'createGame';

    constructor( public gameName: string) {}
}

export class PlayerReadyCommand implements Command {
    public command = 'playerReady';

    constructor( public gameID: string,
        public playerIsReady: boolean) {}
}

// Client commands
export class GameCreatedCommand implements Command {
    public command = 'gameCreated';

    constructor( public gameID: string) {}
}

export class StartGameCommand implements Command {
    public command = 'startGame';

    constructor( public gameID: string ) {}
}

export class RefreshGameListCommand implements Command {
    public command = 'startGame';

    constructor( public gameList: GameList ) {}
}
