import { Command } from './command';
import { ChatMessage } from './model/chat-message';

// CLIENT COMMANDS
export class UpdateChatCommand implements Command {
    public command = 'updateChat';
    public message: ChatMessage;

    constructor(updateChatCommand: any) {
        if (!('message' in updateChatCommand)) {
            throw new TypeError('Unable to deserialize UpdateChatCommand object, ' + JSON.stringify(updateChatCommand));
        }
        this.message = new ChatMessage(updateChatCommand.message);
    }

}

// ----------------------------------------------------------------------------
// SERVER COMMANDS

export class PostChatCommand implements Command {
    public command = 'postChat';

    constructor(public message: string) { }
}
