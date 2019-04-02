import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, AfterViewChecked } from '@angular/core';
import { ChatService } from '../chat.service';
import { UserService } from '@core/user.service';
import { ChatMessage } from '@core/model/chat-message';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '@core/model/user';
import { PlayerService } from 'src/app/game/player.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, AfterViewInit, AfterViewChecked {
  constructor(public chatService: ChatService, public userService: UserService, private playerService: PlayerService) {
  }
  @ViewChild('chatBox') private chatBox: ElementRef;

  chatForm = new FormGroup({
    chatInput: new FormControl('')
  });

  private chatUpdated = false;

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.scrollBottom();
    this.chatService.chatUpdated$.subscribe(event => this.onChatUpdated());
  }

  ngAfterViewChecked(): void {
    if (this.chatUpdated) {
      this.scrollBottom();
      this.chatUpdated = false;
    }
  }

  public onSend() {
    // Get message from form
    const message: string = this.chatForm.get('chatInput').value;
    if (message !== '') {
      this.chatService.sendChat(message);
    }

    // Clear the message
    this.chatForm.get('chatInput').setValue('');
  }

  public onChatUpdated() {
    this.chatUpdated = true;
  }

  public isCurrentUserPost(message: ChatMessage): boolean {
    if (this.userService.user$.value == null) {
      return false;
    }
    return this.userService.user$.value.getUserId() === message.getUserId();
  }

  public onKeydown(event: KeyboardEvent): boolean {
    if (event.key === 'Enter' && !event.shiftKey) {
      this.onSend();
      return false;
    }
    return true;
  }

  private scrollBottom() {
    this.chatBox.nativeElement.scrollTop = this.chatBox.nativeElement.scrollHeight;
  }

  public getMessageTextStyle(message: ChatMessage){
    return message.getIsEvent() ? this.playerService.getMyPlayerCSSColor('color') : {};
  }
}
