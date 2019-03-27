import { Component, OnInit } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';
import { MatDialog, MatSnackBar } from '@angular/material';
import { SelectDestinationCardsDialogComponent, SelectDestinationCardsData } from '../select-destination-cards-dialog/select-destination-cards-dialog.component';
import { CardService } from '../card.service';
import { PlayerNotifierService } from '@core/player-notifier.service';
import { UserService } from '@core/user.service';
import { Router } from '@angular/router';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { RejoinGameCommand } from '@core/game-commands';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  constructor(
    private serverConnection: ServerConnectionService, 
    private serverProxy: ServerProxyService,
    public dialog: MatDialog, private cardService: CardService, 
    private notifierService: PlayerNotifierService, 
    private snackBar: MatSnackBar,
    private userService: UserService,
    private router: Router) {
      
  }

  ngOnInit() {
    // If we haven't been logged in, go to the login page
    if (!this.userService.isLoggedIn) {
      this.router.navigate(['/login']);
      return;
    }

    this.cardService.stagedDestinationCards$.subscribe(result => this.handleNewDestinationCards(result));
    this.notifierService.playerNotification.subscribe(message => this.displayNotification(message));
  
    // Change the game connection to server mode
    this.serverConnection.changeState(new GameState(this.serverConnection));

    if (this.userService.gameid != -1) {
      // This was a rejoin. Send the rejoinGame command
      this.serverProxy.executeCommand(new RejoinGameCommand());
      // TODO: The server should respond immediately - do we need to block the UI meanwhile?
    } else {
      // New game. Request cards
      this.cardService.requestDestinationCards();
    }
  }

  public handleNewDestinationCards(newDestCardsData: SelectDestinationCardsData) {
    const dialogRef = this.dialog.open(SelectDestinationCardsDialogComponent, {
      width: '60%',
      data: newDestCardsData,
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(selectDestCardsResult => {
      console.log('The select cards dialog was closed');
      this.cardService.selectDestinationCards(selectDestCardsResult);
    });
  }

  public displayNotification(message: string) {
    this.snackBar.open(message, '', {duration: 2500});
  }
}
