import { Component, OnInit, Inject } from '@angular/core';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { GameState } from '@core/server/server-connection-state';
import { MatDialog, MatSnackBar, MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material';
import { SelectDestinationCardsDialogComponent, SelectDestinationCardsData } from '../select-destination-cards-dialog/select-destination-cards-dialog.component';
import { CardService } from '../card.service';
import { PlayerNotifierService, IPlayerNotification, TextNotification, ShardNotification } from '@core/player-notifier.service';
import { UserService } from '@core/user.service';
import { Router } from '@angular/router';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { RejoinGameCommand } from '@core/game-commands';
import { GameOverViewData, GameOverDialogComponent } from '../game-over-dialog/game-over-dialog.component';
import { PlayerService } from '../player.service';
import { ShardCard } from '@core/model/cards';
import { ChatService } from 'src/app/chat/chat.service';
import { RouteService } from '../route.service';
import { TurnService } from '../turn/turn.service';

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
    private snackBar: MatSnackBar,
    private notifierService: PlayerNotifierService,
    private userService: UserService,
    private playerService: PlayerService,
    private chatService: ChatService,
    private routeService: RouteService,
    private turnService: TurnService,
    private router: Router) {

  }

  ngOnInit() {
    // If we haven't been logged in, go to the login page
    if (!this.userService.isLoggedIn) {
      this.router.navigate(['/login']);
      return;
    }

    this.cardService.stagedDestinationCards$.subscribe(result => this.handleNewDestinationCards(result));
    this.notifierService.playerNotification$.subscribe(message => this.displayNotification(message));
    this.playerService.playerPointTotals$.subscribe(playerPoints => this.handleEndGame(playerPoints));

    // Change the game connection to server mode
    this.serverConnection.changeState(new GameState(this.serverConnection));

    if (this.userService.gameid !== -1) {
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

  public displayNotification(notification: IPlayerNotification) {
    let snackBarRef: MatSnackBarRef<any>;
    
    if(notification instanceof TextNotification){   // Show simple snack bar
      snackBarRef = this.snackBar.open(notification.msg, '', {duration: notification.displayTime});
    } else if (notification instanceof ShardNotification){  // Show Shard Card snack bar
      snackBarRef = this.snackBar.openFromComponent(ShardCardNotificationComponent, {
        data: notification.msg, duration: notification.displayTime, 
        panelClass: 'shard-snack-bar'
      });
    } else {  // Some other type?
      return;
    }

    snackBarRef.afterDismissed().subscribe(result => this.notifierService.showNext());
  }

  public handleEndGame(gameOverData: GameOverViewData) {
    const dialogRef = this.dialog.open(GameOverDialogComponent, {
      width: '60%',
      data: gameOverData,
      disableClose: false
    });
    dialogRef.afterClosed().subscribe(gameOverDialogResult => {
      this.resetGame();// Clear Data
      this.router.navigate(['/lobby']);
    });
  }

  private resetGame(){  // Reset all game state data
    this.playerService.clearData();
    this.cardService.clearData();
    this.notifierService.clearData();
    this.chatService.clearData();
    this.routeService.clearData();
    this.turnService.clearData();
  }
}

@Component({
  selector: 'shard-card-notification',
  template: `
  <div class="shard-cell">
    <mat-card class="shard-card">
      <img class="shard-icon" src="assets/img/shards/{{card.getImage()}}">
    </mat-card>
    </div>
  `,
  styles: [`
  .shard-cell{
    height: 55px;
    width: 60px;
    margin: auto;
    flex-grow: 0;
  }
  .shard-card{
    height: 40px;
    width: 35px;
    padding: 5px;
    margin: auto;
  }
  .shard-icon{
    height: 35px;
  }
  `],
})
export class ShardCardNotificationComponent {
  card: ShardCard;
  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: ShardCard){
    this.card = data;
  }
}