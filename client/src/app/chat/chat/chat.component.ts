import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ChatService } from '../chat.service';
import { UserService } from '@core/user.service';
import { ChatMessage } from '@core/model/chat-message';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '@core/model/user';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewInit{
  @ViewChild('chatBox') private chatBox: ElementRef;

  constructor(private chatService: ChatService, private userService: UserService) { 
    this.userService.user$.next(new User({'authToken':'auth stuff', 'username':'user1', 'userId':'1234'})); // TODO: Remove this later
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.chatBox.nativeElement.scrollTop = this.chatBox.nativeElement.scrollHeight;
  }

  chatForm = new FormGroup({
    chatInput: new FormControl('', [Validators.required, Validators.maxLength(120)])
  });

  public onSend(){
    // Get message from form
    const message :string = this.chatForm.get('chatInput').value;
    this.chatService.sendChat(new ChatMessage('GAME ID', this.userService.user$.value.getUserId(), this.userService.user$.value.getUsername(), message));
  }

  public isCurrentUserPost(message: ChatMessage): boolean{
    if (this.userService.user$.value == null){
      return false;
    }
    return this.userService.user$.value.getUserId() === message.getuserId();
  }
}
