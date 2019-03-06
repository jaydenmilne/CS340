import { Injectable } from '@angular/core';
import { ChatMessage } from '@core/model/chat-message';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { PostChatCommand, UpdateChatCommand } from '@core/chat-commands';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private server: ServerProxyService) { }

  public chatHistory: ChatMessage[] = [];

  public sendChat(message: string) {
    const command: PostChatCommand = new PostChatCommand(message);
    this.server.executeCommand(command);
  }

  public handleUpdateChat(command: UpdateChatCommand) {
    this.chatHistory.push(new ChatMessage(command.userId, command.userName, command.message, command.sequenceNum));
  }

}
