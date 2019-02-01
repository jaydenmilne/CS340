import { Injectable } from '@angular/core';
import { GameList } from '../core/model/GameLIst';
import { GamePreview } from '../core/model/GamePreview';
import { Player } from '../core/model/Player';
import { ServerProxyService } from '@core/server-proxy.service';
import { JoinGameCommand, CreateGameCommand, LeaveGameCommand, PlayerReadyCommand, GameCreatedCommand, StartGameCommand, RefreshGameListCommand, ListGamesCommand } from '@core/lobbycommands';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor(private server: ServerProxyService) { }

  public getGamesList(){
    const command: ListGamesCommand = new ListGamesCommand();
    this.server.transmitCommand(command);
  }

  public joinGame(game: GamePreview){
    // Create Join Game command
    const command: JoinGameCommand = new JoinGameCommand(game.getID());
    this.server.transmitCommand(command);
  }

  public createGame(name: string) {
    const command: CreateGameCommand = new CreateGameCommand(name);
    this.server.transmitCommand(command);
  }

  public leaveGame(game: GamePreview){
    const command: LeaveGameCommand = new LeaveGameCommand(game.getID());
    this.server.transmitCommand(command);
  }

  public setReady(player: Player, ready: boolean, game: GamePreview){
    const command: PlayerReadyCommand = new PlayerReadyCommand(game.getID(), ready);
    this.server.transmitCommand(command);
  }

  public onStartGameCommand(command: StartGameCommand){

  }

  public onGameCreatedCommand(command: GameCreatedCommand){

  }

  public onRefreshGameListCommand(command: RefreshGameListCommand){

  }
}
