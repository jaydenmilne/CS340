import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

import { LoginModule } from './login/login.module';
import { LobbyModule } from './lobby/lobby.module';
import { GameModule } from './game/game.module';
import { ChatModule } from './chat/chat.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { FatalErrorDialogComponent } from './app.component';
import { WINDOW_PROVIDERS } from '@core/server/window-provider';

@NgModule({
  declarations: [
    AppComponent,
    FatalErrorDialogComponent
  ],
  entryComponents: [ FatalErrorDialogComponent ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LoginModule,
    LobbyModule,
    ChatModule,
    GameModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    AppRoutingModule
  ],
  providers: [ServerProxyService, WINDOW_PROVIDERS],
  bootstrap: [AppComponent]
})
export class AppModule { }
