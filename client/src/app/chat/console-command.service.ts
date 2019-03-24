import { Injectable } from '@angular/core';
import { CardService } from '../game/card.service';
import { PlayerService } from '../game/player.service';
import { ShardCardDeck, ShardCard, DestinationCardDeck, DestinationCard } from '@core/model/cards';
import { MaterialType } from "@core/model/material-type.enum";
import { UserService } from '@core/user.service';
import { RouteService } from '../game/route.service';

import { RouteName } from "@core/model/route-name.enum";
import { CommandRouterService } from '@core/command-router.service';
import { DealCardsCommand } from '@core/game-commands';

@Injectable({
  providedIn: 'root'
})
export class ConsoleCommandService {

  constructor(private playerService: PlayerService, private cardService: CardService, private myUser: UserService, private routeService: RouteService, private commandRouter: CommandRouterService) {
   }

  public parseCommand(message: string) {
    const first = message.split(' ')[0];
    switch (first) {
      case '/rainbowroad': {
        this.rainbowCommand();
        break;
      }
      case '/allyourbasesaremine': {
        this.allYourBasesCommand();
        break;
      }
      case '/claim': {
        this.claimCommand(message);
        break;
      }
      case '/givethemtrainsorgivemedeath': {
        this.giveThemTrains();
        break;
      }
      case'/myturn': {
        this.changeTurn(message);
        break;
      }
      case'/heartinthecards': {
        this.killDecks();
        break;
      }
      case'/finaldestination': {
        this.finalDestination();
        break;
      }
      case'/newroad': {
        this.newRoad(message);
        break;
      }
      case'/whereto': {
        this.whereTo(message);
        break;
      }
      case'/makeitrain': {
        this.makeItRain(message);
        break;
      }
      default: {
        break;
      }
    }

  }

  private rainbowCommand() {
    this.cardService.faceUpShardCards = new ShardCardDeck([new ShardCard({'type': 'INFINITY_GAUNTLET'}),
        new ShardCard({'type': 'INFINITY_GAUNTLET'}), new ShardCard({'type': 'INFINITY_GAUNTLET'}),
        new ShardCard({'type': 'INFINITY_GAUNTLET'}), new ShardCard({'type': 'INFINITY_GAUNTLET'})]);
  }

  private allYourBasesCommand() {
    this.playerService.players.forEach(function (value) {
      value.points = -100;
    });
    this.playerService.playersById.get(this.myUser.user$.value.getUserId()).points = 1000;
  }

  private claimCommand(message: string) {
      const player = message.split(' ')[1];
      const route = message.split(' ')[2];
      let userId;
      this.playerService.players.forEach(function (value) {
        if (value.username === player) {
          userId = value.userId;
        }

      });
      if (userId != null) {
      this.routeService.updateOwnership(route as RouteName, userId);
      }
  }

  private changeTurn(message) {
    const player = message.split(' ')[1];
    let userId;
    this.playerService.players.forEach(function (value) {
      if (value.username === player) {
        userId = value.userId;
      }
    });
    if (userId != null) {
    this.playerService.activePlayerId = userId;
    }
  }

  private giveThemTrains() {
    this.playerService.players.forEach(function (value) {
      value.numRemainingTrains = 100;
    });
    this.playerService.playersById.get(this.myUser.user$.value.getUserId()).numRemainingTrains = 10;
  }

  private killDecks() {
    this.cardService.destCardDeckSize = 0;
    this.cardService.shardCardDeckSize = 0;
    this.cardService.shardCardDiscardSize = 47;
    this.playerService.players.forEach(function (value) {
      value.numDestinationCards = 30;
      value.numTrainCards = 0;
    });
    for (let i = 0; i < 100; i++) {
    this.cardService.playerTrainCards.cards.push(new ShardCard({'type': 'INFINITY_GAUNTLET'}));
    }

  }

  private finalDestination() {
    this.cardService.playerDestCards = new DestinationCardDeck([]);
  }

  private newRoad(message: string) {
    const cityOne = message.split(' ')[1];
    const cityTwo = message.split(' ')[2];
    const points = message.split(' ')[3];
    const ends = [cityOne, cityTwo];
    this.cardService.playerDestCards.cards.push(new DestinationCard({'cities': ends, 'points': points}));
  }

  private whereTo(message: string) {
    this.commandRouter.dealCards$.next(new DealCardsCommand({
      'shardCards': [],
      'destinations': [
        new DestinationCard({'cities': ['gibborim', 'avengershq'], 'points': 22}),
        new DestinationCard({'cities': ['gibborim', 'knowhere'], 'points': 9}),
        new DestinationCard({'cities': ['galactus', 'quantumrealm'], 'points': 17}),
      ],
      'minDestinations': 2
    }));
  }

  private makeItRain(message: string) {
    const args = message.split(' ');
    let shards;
    if (args.length > 1) {
      const cardType = args[1].toUpperCase();
      if (MaterialType[cardType] === undefined) { return; }

      shards = [
        { 'type': cardType }, { 'type': cardType }, { 'type': cardType },
      ];
    } else {
      shards = [
        { 'type': 'VIBRANIUM' }, { 'type': 'VIBRANIUM' }, { 'type': 'VIBRANIUM' },
        { 'type': 'INFINITY_GAUNTLET' }, { 'type': 'INFINITY_GAUNTLET' }, { 'type': 'INFINITY_GAUNTLET' },
        { 'type': 'REALITY_SHARD' }, { 'type': 'REALITY_SHARD' }, { 'type': 'REALITY_SHARD' },
        { 'type': 'SOUL_SHARD' }, { 'type': 'SOUL_SHARD' }, { 'type': 'SOUL_SHARD' },
        { 'type': 'SPACE_SHARD' }, { 'type': 'SPACE_SHARD' }, { 'type': 'SPACE_SHARD' },
        { 'type': 'MIND_SHARD' }, { 'type': 'MIND_SHARD' }, { 'type': 'MIND_SHARD' },
        { 'type': 'POWER_SHARD' }, { 'type': 'POWER_SHARD' }, { 'type': 'POWER_SHARD' },
        { 'type': 'TIME_SHARD' }, { 'type': 'TIME_SHARD' }, { 'type': 'TIME_SHARD' },
        { 'type': 'PALLADIUM' }, { 'type': 'PALLADIUM' }, { 'type': 'PALLADIUM' },
      ];
    }

    this.commandRouter.dealCards$.next(new DealCardsCommand({
      'shardCards': shards,
      'destinations': [],
      'minDestinations': 0
    }));
  }

}
