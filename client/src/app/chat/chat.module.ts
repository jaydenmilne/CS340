import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat/chat.component';
import { MatCardModule } from '@angular/material';
import { ChatService } from './chat.service';

@NgModule({
  declarations: [ChatComponent],
  imports: [
    CommonModule, MatCardModule
  ],
  exports: [ChatComponent],
  providers: [ ChatService ]
})
export class ChatModule { }
