import { Injectable } from '@angular/core';
import { ShardCard, DestinationCard } from '@core/model/cards';
import { CommandRouterService } from '@core/command-router.service';
import { UpdatePlayerCommand } from '@core/game-commands';
import { ServerProxyService } from '@core/server/server-proxy.service';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  constructor(private commandRouter : CommandRouterService,
    private serverProxy : ServerProxyService) {
    
    this.serverProxy.executeCommand(new RequestDestinationsCommand());
    this.commandRouter.updatePlayer$.subscribe( cmd => this.onUpdatePlayer(cmd));
  }

  private onUpdatePlayer(updatePlayerCmd : UpdatePlayerCommand) {
    
  }

  public faceUpShardCards : ShardCard[];
  public shardCardDeckSize : number;
  public shardCardDiscardSize : number;
  public destCardDeckSize : number;
  public playerTrainCards : ShardCard[];
  public playerDestCards : DestinationCard[];

}
