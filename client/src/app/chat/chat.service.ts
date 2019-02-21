import { Injectable } from '@angular/core';
import { ChatMessage } from '@core/model/chat-message';
import { ServerProxyService } from '@core/server-proxy.service';
import { PostChatCommand, UpdateChatCommand } from '@core/chat-commands';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private server: ServerProxyService) { }

  public chatHistory: ChatMessage[] = [
    new ChatMessage(8652315, 'riffraff78', 'This is a message', 0),
    new ChatMessage(86515, 'toughstuff56', 'This is a message', 1),
    new ChatMessage(86525, 'riffraff78', 'This is a message', 2),
    new ChatMessage(86525, 'toughstuff56', 'This is a message', 3),
    new ChatMessage(8655, 'hotshot33', 'This is a message', 4),
    new ChatMessage(865295, 'riffraff78', 'This is a message', 5),
    new ChatMessage(86525, 'toughstuff56', 'This is a message', 6),
    new ChatMessage(1234, 'user1', 'This is a message', 7),
    new ChatMessage(8652315, 'hotshot33', 'This is a message', 8),
    new ChatMessage(865215, 'hotshot33', 'This is a message', 9),
    new ChatMessage(1234, 'user1', 'This is a message', 10),
    new ChatMessage(815, 'hotshot33', 'This is a message', 11),
    new ChatMessage(1234, 'user1', 'This is another message', 12),
    new ChatMessage(8652315, 'hotshot33', 'This is a really loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong message', 13)
  ];

  public sendChat(message: string){
    const command: PostChatCommand = new PostChatCommand(message);
    this.server.transmitCommand(command);
  }

  public handleUpdateChat(command: UpdateChatCommand){
    this.chatHistory.push(new ChatMessage(command.userId, command.userName, command.message, command.sequenceNum))
  }

}
