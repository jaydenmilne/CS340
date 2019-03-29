import { Command } from './command';
import { User } from './model/user';

// ----------------------------------------------------------------------------
// SERVER COMMANDS

export class RegisterCommand implements Command {
    public command = 'register';

    constructor( public username: string,
                 public password: string ) {}
}

export class LoginCommand implements Command {
    public command = 'login';

    constructor( public username: string,
                 public password: string ) {}
}

// ----------------------------------------------------------------------------
// CLIENT COMMANDS
// (need to have verification in the constructor verified)
export class LoginResult implements Command {
    public command = 'loginResult';
    public error: string;
    public user: User;
    public game : number;

    constructor(loginResult: any) {
        if (!('error' in loginResult) ||
            !('user' in loginResult) ||
            !('game' in loginResult)) {
                throw new TypeError('Unable to deserialize LoginResult object, ' + JSON.stringify(loginResult));
        }

        this.error = loginResult.error;
        this.game = loginResult.game;
        if (this.error === '') {
            this.user = new User(loginResult.user);
        } else {
            this.user = null;
        }
    }
}
