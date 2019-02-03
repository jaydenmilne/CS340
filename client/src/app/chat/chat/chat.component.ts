import { Component, OnInit } from '@angular/core';
import { ChatService } from '../chat.service';
import { UserService } from '@core/user.service';
import { ChatMessage } from '@core/model/chat-message';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private userService: UserService) { }

  ngOnInit() {
  }

  public onSend(){
    // Get message from form
    this.chatService.sendChat(new ChatMessage('GAME ID', this.userService.user$.value.getUserId(), this.userService.user$.value.getUsername(), 'message'));
  }

  public isCurrentUserPost(message: ChatMessage): boolean{
    if (this.userService.user$.value == null){
      return false;
    }
    return this.userService.user$.value.getUserId() === message.getuserId();
  }
}
