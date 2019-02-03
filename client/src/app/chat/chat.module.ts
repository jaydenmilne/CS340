import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat/chat.component';
import { MatCardModule, MatButtonModule } from '@angular/material';
import { ChatService } from './chat.service';

@NgModule({
  declarations: [ChatComponent],
  imports: [
    CommonModule, MatCardModule, MatButtonModule
  ],
  exports: [ChatComponent],
  providers: [ ChatService ]
})
export class ChatModule { }
