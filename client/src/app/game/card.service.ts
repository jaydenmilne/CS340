import { Injectable } from '@angular/core';
import { ShardCard, DestinationCard } from '@core/model/cards';
import { CommandRouterService } from '@core/command-router.service';
import { UpdatePlayerCommand, DealCardsCommand, UpdateBankCommand, DiscardDestinationsCommand } from '@core/game-commands.ts';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { RequestDestinationsCommand } from '@core/game-commands.ts'
import { Subject } from 'rxjs';

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

  public stagedDestinationCards$ = new Subject<DealCardsCommand>();
  public stagedShardCards$ = new Subject<DealCardsCommand>();

  private onDealCards(dealCardsCmd : DealCardsCommand) {
    if (dealCardsCmd.destinations.length > 0) {
      this.stagedDestinationCards$.next(dealCardsCmd);
    }
    if (dealCardsCmd.shardCards.length > 0) {
      this.stagedShardCards$.next(dealCardsCmd);
    }
  }

  private onUpdateBank(updateBankCmd : UpdateBankCommand) {
    this.faceUpShardCards = updateBankCmd.faceUpCards;
    this.shardCardDeckSize = updateBankCmd.shardDrawPileSize;
    this.shardCardDiscardSize = updateBankCmd.shardDiscardPileSize;
    this.destCardDeckSize = updateBankCmd.destinationPileSize;
  }

  private onUpdatePlayer(updatePlayerCmd : UpdatePlayerCommand) {
    let gamePlayer = updatePlayerCmd.player;
  }

  public selectDestinationCards(selectedCards : DestinationCard[], discardedCards : DestinationCard[]) {
    let outgoingCommand = new DiscardDestinationsCommand(discardedCards);
    this.serverProxy.executeCommand(outgoingCommand);

    this.playerDestCards = this.playerDestCards.concat(selectedCards);
  }
}
