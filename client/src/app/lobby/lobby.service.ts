import { Injectable } from '@angular/core';
import { GameList } from '../core/model/game-list';
import { GamePreview } from '../core/model/game-preview';
import { Player } from '../core/model/player';
import { ServerProxyService } from '@core/server-proxy.service';
import { JoinGameCommand, CreateGameCommand, LeaveGameCommand, PlayerReadyCommand, GameCreatedCommand, StartGameCommand, RefreshGameListCommand, ListGamesCommand, ChangeColorCommand } from '@core/lobby-commands';
import { Color } from '@core/model/color.enum';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor(private server: ServerProxyService) {
      // this.gameList.setSelectedGameByID('asldk');
   }

  public gameList: GameList = new GameList([
    new GamePreview('game1', 'asldk', false, [
      new Player(Color.YELLOW, 'user1', false, 'riffraff78'),
      new Player(Color.BLUE, 'user2', true, 'toughstuff56'),
      new Player(Color.GREEN, 'user3', true, 'hotshot33'),
      new Player(Color.PURPLE, 'user4', false, 'tooslow64'),
    ]),
    new GamePreview('game2', '531', false, []),
    new GamePreview('game3', 'vcb', false, []),
  ]);

  public getGamesList() {
    const command: ListGamesCommand = new ListGamesCommand();
    this.server.transmitCommand(command);
  }

  public joinGame(game: GamePreview) {
    // Create Join Game command
    const command: JoinGameCommand = new JoinGameCommand(game.getID());
    this.server.transmitCommand(command);
  }

  public createGame(name: string) {
    const command: CreateGameCommand = new CreateGameCommand(name);
    this.server.transmitCommand(command);
  }

  public leaveGame() {
    const command: LeaveGameCommand = new LeaveGameCommand(this.gameList.getSelectedGame().getID());
    this.gameList.setSelectedGame(undefined);
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

  }

  public onGameCreatedCommand(command: GameCreatedCommand) {

  }

  public onRefreshGameListCommand(command: RefreshGameListCommand) {

  }
}
