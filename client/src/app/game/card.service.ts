import { Injectable } from '@angular/core';
import { ShardCardDeck, DestinationCardDeck, ShardCard } from '@core/model/cards';
import { CommandRouterService } from '@core/command-router.service';
import { DealCardsCommand, UpdateBankCommand, DiscardDestinationsCommand, UpdateHandCommand, DrawShardCard } from '@core/game-commands.ts';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { RequestDestinationsCommand } from '@core/game-commands.ts';
import { Subject } from 'rxjs';
import { SelectDestinationCardsResult, SelectDestinationCardsData } from './select-destination-cards-dialog/select-destination-cards-dialog.component';
import { TurnService } from './turn/turn.service';
import { MaterialType } from '@core/model/material-type.enum';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  public shardCardDeckSize: number;
  public shardCardDiscardSize: number;
  public destCardDeckSize: number;

  public faceUpShardCards: ShardCardDeck = new ShardCardDeck([]);

  public playerTrainCards: ShardCardDeck = new ShardCardDeck([]);
  public playerDestCards: DestinationCardDeck = new DestinationCardDeck([]);

  // Temp storage location, used with the SelectDestCard dialog
  public stagedDestinationCards$ = new Subject<SelectDestinationCardsData>();

  constructor(
    private commandRouter: CommandRouterService,
    private serverProxy: ServerProxyService,
    private turnService: TurnService) {

    this.commandRouter.dealCards$.subscribe( cmd => this.onDealCards(cmd));
    this.commandRouter.updateBank$.subscribe( cmd => this.onUpdateBank(cmd));
    this.commandRouter.updateHand$.subscribe( cmd => this.onUpdateHand(cmd));
  }

  /**
   * Callback for when a the server deals cards to the client.
   * If destination cards are sent, will signal the select dest cards dialog
   * @param dealCardsCmd Cards to process
   */
  private onDealCards(dealCardsCmd: DealCardsCommand) {
    // If the server send destination cards, signal the popup
    if (dealCardsCmd.destinations.length > 0) {
      this.stagedDestinationCards$.next(
        new SelectDestinationCardsData(
          new DestinationCardDeck(dealCardsCmd.destinations),
          dealCardsCmd.minDestinations));
    }
    // Add the new train cards to the bank
    this.playerTrainCards = new ShardCardDeck(this.playerTrainCards.cards.concat(dealCardsCmd.shardCards));
  }

  private onUpdateBank(updateBankCmd: UpdateBankCommand) {
    this.faceUpShardCards = new ShardCardDeck(updateBankCmd.faceUpCards);
    this.shardCardDeckSize = updateBankCmd.shardDrawPileSize;
    this.shardCardDiscardSize = updateBankCmd.shardDiscardPileSize;
    this.destCardDeckSize = updateBankCmd.destinationPileSize;
  }

  public selectDestinationCards(selectCardsResult: SelectDestinationCardsResult) {
    this.serverProxy.executeCommand( new DiscardDestinationsCommand(selectCardsResult.discardedCards.cards));

    this.playerDestCards = new DestinationCardDeck(this.playerDestCards.cards.concat(selectCardsResult.selectedCards.cards));
  }

  public requestDestinationCards() {
    this.serverProxy.executeCommand(new RequestDestinationsCommand());
  }

  private onUpdateHand(updateHandCmd: UpdateHandCommand) {
    this.playerDestCards = new DestinationCardDeck(updateHandCmd.destinations);
    this.playerTrainCards = new ShardCardDeck(updateHandCmd.shardCards);
  }

  public drawFaceUpShardCard(card: ShardCard) {
    if (card.type == MaterialType.INFINITY_GAUNTLET) {
      if (this.turnService.canDrawWild()) {
        this.turnService.onDrawFaceUpWildCard();

        this.serverProxy.executeCommand(new DrawShardCard(card.type));
      }
    } else {
      if (this.turnService.canDrawShards()) {
        this.turnService.onDrawFaceUpShardCard();

        this.serverProxy.executeCommand(new DrawShardCard(card.type));
      }
    }
  }

  public drawShardCardFromDeck() {
    if (this.turnService.canDrawShards()) {
      this.turnService.onDrawDeckShardCard();

      this.serverProxy.executeCommand(new DrawShardCard('deck'));
    }
  }

  public drawDestCardFromDeck() {
    if (this.turnService.canDrawDestinations()) {
      this.turnService.onDrawDestCard();

      this.serverProxy.executeCommand(new RequestDestinationsCommand());
    }
  }
}
