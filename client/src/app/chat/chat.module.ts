import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatComponent } from './chat/chat.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ChatService } from './chat.service';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { UserService } from '@core/user.service';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ChatComponent],
  imports: [
    CommonModule, MatCardModule, MatButtonModule, MatInputModule, MatFormFieldModule, FormsModule, ReactiveFormsModule
  ],
  exports: [ChatComponent],
  providers: [ ChatService, ServerProxyService, UserService ]

})
export class ChatModule { }
