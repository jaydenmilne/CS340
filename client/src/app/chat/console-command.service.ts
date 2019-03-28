import { Injectable } from '@angular/core';
import { CardService } from '../game/card.service';
import { PlayerService } from '../game/player.service';
import { ShardCardDeck, ShardCard, DestinationCardDeck, DestinationCard } from '@core/model/cards';
import { MaterialType } from '@core/model/material-type.enum';
import { UserService } from '@core/user.service';
import { RouteService } from '../game/route.service';
import { RouteName } from '@core/model/route-name.enum';
import { CommandRouterService } from '@core/command-router.service';
import { DealCardsCommand, DebugHelpCommand } from '@core/game-commands';
import { ServerProxyService } from '@core/server/server-proxy.service';

@Injectable({
  providedIn: 'root'
})
export class ConsoleCommandService {

  constructor(private serverProxy: ServerProxyService) {
   }

  public parseCommand(message: string) {
    this.serverProxy.executeCommand(new DebugHelpCommand(message));
  }
  /*
  rainbowroad makes all five faceup cards infinitygauntlets
  allyourbasesaremine takes all routes owned by other players and gives them to user
  claim [player] [routeId] claims said route for said user
  givethemtrainsorgivemedeath makes everyones trains 100 except the user who is left with 10
  myturn advances whose turn it is
  heartinthecards gives user a full deck of shardcards in his hand an removes everyone elses cards
  finaldestination gives all other players a deck of destination cards
  newroad [city1] [city2] [points] makes a new destination card for the user with those cities & points
  whereto starts a draw on destination cards for a user
  makeitrain [type] if the type is empty the user gets three of each type of card if it isn't the
  user gets three of that type of card
  */
}
