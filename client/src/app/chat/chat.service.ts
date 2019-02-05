import { Injectable } from '@angular/core';
import { ChatMessage } from '@core/model/chat-message';
import { ServerProxyService } from '@core/server-proxy.service';
import { PostChatCommand } from '@core/chat-commands';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private server: ServerProxyService) { }

  public chatHistory: ChatMessage[] = [
    new ChatMessage('game1', '8652315', 'riffraff78', 'This is a message'),
    new ChatMessage('game1', '865sdf15', 'toughstuff56', 'This is a message'),
    new ChatMessage('game1', '8652gf5', 'riffraff78', 'This is a message'),
    new ChatMessage('game1', '8652cv5', 'toughstuff56', 'This is a message'),
    new ChatMessage('game1', '8652re5', 'hotshot33', 'This is a message'),
    new ChatMessage('game1', '8652df5', 'riffraff78', 'This is a message'),
    new ChatMessage('game1', '8652vcxb5', 'toughstuff56', 'This is a message'),
    new ChatMessage('game1', '1234', 'user1', 'This is a message'),
    new ChatMessage('game1', '865231xcv5', 'hotshot33', 'This is a message'),
    new ChatMessage('game1', '865231xzv5', 'hotshot33', 'This is a message'),
    new ChatMessage('game1', '1234', 'user1', 'This is a message'),
    new ChatMessage('game1', '86523gfh15', 'hotshot33', 'This is a message'),
    new ChatMessage('game1', '1234', 'user1', 'This is another message'),
    new ChatMessage('game1', '865231tyu5', 'hotshot33', 'This is a really loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong message')
  ];

  public sendChat(message: ChatMessage){
    const command: PostChatCommand = new PostChatCommand(message.getgameId(), message.getuserId(), message.getUserName(), message.getMessage());
    this.server.transmitCommand(command);
  }

  public handlePostChat(command: PostChatCommand){
    this.chatHistory.push(new ChatMessage(command.gameId, command.userId, command.userName, command.message))
  }

}
