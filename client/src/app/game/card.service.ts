import { Injectable } from '@angular/core';
import { ShardCard, DestinationCard } from '@core/model/cards';
import { CommandRouterService } from '@core/command-router.service';
import { UpdatePlayerCommand } from '@core/game-commands.ts';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { RequestDestinationsCommand } from '@core/game-commands.ts'

@Injectable({
  providedIn: 'root'
})
export class CardService {

  constructor(private commandRouter : CommandRouterService,
    private serverProxy : ServerProxyService) {
    
    this.serverProxy.executeCommand(new RequestDestinationsCommand());
    this.commandRouter.dealCards$.subscribe( cmd => this.onDealCards(cmd));
    this.commandRouter.updateBank$.subscribe( cmd => this.onUpdateBank(cmd));
    this.commandRouter.updatePlayer$.subscribe( cmd => this.onUpdatePlayer(cmd));
  }

  public faceUpShardCards : ShardCard[];
  public shardCardDeckSize : number;
  public shardCardDiscardSize : number;
  public destCardDeckSize : number;
  public playerTrainCards : ShardCard[];
  public playerDestCards : DestinationCard[];

  private onDealCards(dealCardsCmd : DealCardsCommand) {
    // Need to set array of cards as observable (like in CommandRouter)
  }

  private onUpdateBank(updateBankCmd : UpdateBankCommand) {

  }

  private onUpdatePlayer(updatePlayerCmd : UpdatePlayerCommand) {
    let gamePlayer = updatePlayerCmd.player;
    
  }
}
