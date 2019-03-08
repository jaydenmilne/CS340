import { Command } from './command';

// CLIENT COMMANDS
export class UpdateChatCommand implements Command {
    public command = 'updateChat';
    public userId: number;
    public username: string;
    public message: string;
    public sequenceNum: number;

    constructor(updateChatCommand: any) {
        if (!('userId' in updateChatCommand &&
            'username' in updateChatCommand &&
            'message' in updateChatCommand && 
            'sequenceNum' in updateChatCommand)) {
            throw new TypeError('Unable to deserialize UpdateChatCommand object, ' + JSON.stringify(updateChatCommand));
        }
        this.userId = updateChatCommand.userId;
        this.username = updateChatCommand.username;
        this.message = updateChatCommand.message;
        this.sequenceNum = updateChatCommand.sequenceNum;

    }

}

// ----------------------------------------------------------------------------
// SERVER COMMANDS

export class PostChatCommand implements Command {
    public command = 'postChat';

    constructor(public message: string) { }
}
