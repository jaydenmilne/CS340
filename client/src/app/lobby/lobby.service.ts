import { Injectable } from '@angular/core';
import { GameList } from '../core/model/game-list';
import { GamePreview } from '../core/model/game-preview';
import { Player } from '../core/model/player';
import { ServerProxyService } from '@core/server-proxy.service';
import { JoinGameCommand, CreateGameCommand, LeaveGameCommand, PlayerReadyCommand, GameCreatedCommand, StartGameCommand, RefreshGameListCommand, ListGamesCommand, ChangeColorCommand } from '@core/lobby-commands';
import { Color } from '@core/model/color.enum';
import { CommandRouterService } from '@core/command-router.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor(private server: ServerProxyService, private commandRouter: CommandRouterService, private router: Router) {
      // this.gameList.setSelectedGameByID('asldk');
      this.commandRouter.gameCreated$.subscribe(
        result => this.onGameCreatedCommand(result)
      );
      this.commandRouter.startGame$.subscribe(
        result => this.onStartGameCommand(result)
      );
      this.commandRouter.refreshGameList$.subscribe(
        result => this.onRefreshGameListCommand(result)
      );
   }

  public gameList: GameList = new GameList([
    new GamePreview({'name':'game1', 'Id':'asldk', 'started':false, 'players':[
      new Player({'color':Color.YELLOW, 'userId':'user1', 'ready':false, 'name':'riffraff78'}),
      new Player({'color':Color.BLUE, 'userId':'user2', 'ready':true, 'name':'toughstuff56'}),
      new Player({'color':Color.GREEN, 'userId':'user3', 'ready':true, 'name':'hotshot33'}),
      new Player({'color':Color.PURPLE, 'userId':'user4', 'ready':false, 'name':'tooslow64'}),
    ]}),
    new GamePreview({'name':'game2', 'Id':'531', 'started':false, 'players':[]}),
    new GamePreview({'name':'game3', 'Id':'vcb', 'started':false, 'players':[]}),
  ]);
  private lastSelectedId: string = "";

  public getGamesList() {
    const command: ListGamesCommand = new ListGamesCommand();
    this.server.transmitCommand(command);
  }

  public joinGame(game: GamePreview) {
    // Create Join Game command
    const command: JoinGameCommand = new JoinGameCommand(game.getID());
    this.lastSelectedId = game.getID();
    this.setSelectedById(this.lastSelectedId);
    this.server.transmitCommand(command);
  }

  public createGame(name: string) {
    const command: CreateGameCommand = new CreateGameCommand(name);
    this.server.transmitCommand(command);
  }

  public leaveGame() {
    const command: LeaveGameCommand = new LeaveGameCommand(this.gameList.getSelectedGame().getID());
    this.lastSelectedId = undefined;
    this.gameList.setSelectedGameByID(this.lastSelectedId);
    this.server.transmitCommand(command);
  }

  public setReady(ready: boolean) {
    const command: PlayerReadyCommand = new PlayerReadyCommand(this.gameList.getSelectedGame().getID(), ready);
    this.server.transmitCommand(command);
  }

  public changeColor(color: Color) {
    const command: ChangeColorCommand =  new ChangeColorCommand(this.gameList.getSelectedGame().getID(), color);
    this.server.transmitCommand(command);
  }

  public onStartGameCommand(command: StartGameCommand) {
      // navigate to /game/gameId
      this.router.navigate(['/game/' + command.gameId]);
  }

  public onGameCreatedCommand(command: GameCreatedCommand) {
    this.lastSelectedId = command.gameId;
    this.setSelectedById(command.gameId);
  }

  public onRefreshGameListCommand(command: RefreshGameListCommand) {
    this.gameList = new GameList(command.games);
    this.setSelectedById(this.lastSelectedId);
  }

  private setSelectedById(gameId: string){
    if(this.gameList !== undefined){
      this.gameList.setSelectedGameByID(this.lastSelectedId);
    }
  }
}
