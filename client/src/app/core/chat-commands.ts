import { Command } from './command';

// ----------------------------------------------------------------------------
// SERVER COMMANDS

export class PostChatCommand implements Command {
    public command = 'postChat';

    constructor( public gameId: string,
                 public userId: string,
                 public userName: string,
                 public message: string ) {}
}
