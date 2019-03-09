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
export class ChatComponent implements OnInit, AfterViewInit {

  constructor(private chatService: ChatService, private userService: UserService) {
  }
  @ViewChild('chatBox') private chatBox: ElementRef;

  chatForm = new FormGroup({
    chatInput: new FormControl('')
  });

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.chatBox.nativeElement.scrollTop = this.chatBox.nativeElement.scrollHeight;
  }

  public onSend() {
    // Get message from form
    const message: string = this.chatForm.get('chatInput').value;
    this.chatService.sendChat(message);

    // Clear the message
    this.chatForm.get("chatInput").setValue("");
  }

  public isCurrentUserPost(message: ChatMessage): boolean {
    if (this.userService.user$.value == null) {
      return false;
    }
    return this.userService.user$.value.getUserId() === message.getUserId();
  }

  public onKeydown(event : KeyboardEvent) : boolean {
    if (event.key == "Enter" && !event.shiftKey) {
      this.onSend()
      return false;
    }
    return true;
  }
}
