import { Injectable } from '@angular/core';
import { ChatMessage } from '@core/model/chat-message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor() { }

  public chatHistory: ChatMessage[] = [
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user1', 'This is a message'),
    new ChatMessage('game1', 'user2', 'This is another message'),
    new ChatMessage('game1', 'user3', 'This is a really loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong message')
  ];

  public sendChat(message: ChatMessage){

  }

  public postNewChats(messages: ChatMessage[]){

  }

}
