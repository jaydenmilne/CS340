import { Command } from './command';

// CLIENT COMMANDS
export class UpdateChatCommand implements Command {
    public command = 'updateChat';

    constructor(public userId: number,
        public userName: string,
        public message: string,
        public sequenceNum: number) { }
}

// ----------------------------------------------------------------------------
// SERVER COMMANDS

export class PostChatCommand implements Command {
    public command = 'postChat';

    constructor(public message: string) { }
}
