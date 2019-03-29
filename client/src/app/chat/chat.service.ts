import { Injectable, EventEmitter } from '@angular/core';
import { ChatMessage } from '@core/model/chat-message';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { PostChatCommand, UpdateChatCommand } from '@core/chat-commands';
import { CommandRouterService } from '@core/command-router.service';
import { ConsoleCommandService } from './console-command.service';


@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private server: ServerProxyService, private commandRouter: CommandRouterService, private console: ConsoleCommandService) {
    this.commandRouter.updateChat$.subscribe(updateChat => this.handleUpdateChat(updateChat));
  }

  public chatHistory: ChatMessage[] = [];
  public chatUpdated$: EventEmitter<any> = new EventEmitter();

  public sendChat(message: string) {
    if (message.charAt(0) === '/') {
      this.console.parseCommand(message);
    } else {
    const command: PostChatCommand = new PostChatCommand(message);
    this.server.executeCommand(command);
    }

  }

  public handleUpdateChat(command: UpdateChatCommand) {
    this.chatHistory.push(command.message);
    this.chatUpdated$.emit(null);
  }

}
