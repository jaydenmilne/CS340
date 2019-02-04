import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

import { LoginModule } from './login/login.module';
import { LobbyModule } from './lobby/lobby.module';
import { GameModule } from './game/game.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatDialogModule, MatButtonModule } from '@angular/material';
import { ServerProxyService } from '@core/server-proxy.service';
import { FatalErrorDialogComponent } from './app.component';

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
    GameModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    AppRoutingModule
  ],
  providers: [ServerProxyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
