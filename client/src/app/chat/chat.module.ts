import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat/chat.component';
import { MatCardModule, MatButtonModule } from '@angular/material';
import { ChatService } from './chat.service';
import { ServerProxyService } from '@core/server-proxy.service';
import { UserService } from '@core/user.service';

@NgModule({
  declarations: [ChatComponent],
  imports: [
    CommonModule, MatCardModule, MatButtonModule
  ],
  exports: [ChatComponent],
  providers: [ ChatService, ServerProxyService, UserService ]
  
})
export class ChatModule { }
