import { Injectable } from '@angular/core';
import { GameList } from '../core/model/GameLIst';
import { GamePreview } from '../core/model/GamePreview';
import { Player } from '../core/model/Player';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor() { }

  public getGamesList():GameList{
    return new GameList([]);
  }

  public joinGame(game: GamePreview){
    
  }

  public createGame(): GamePreview {
    return new GamePreview('', '', false, []);
  }

  public leaveGame(game: GamePreview){

  }

  public setReady(player: Player, ready: boolean, game: GamePreview){
    
  }
}
