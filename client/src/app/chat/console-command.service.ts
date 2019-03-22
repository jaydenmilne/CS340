import { Injectable } from '@angular/core';
import { CardService } from '../game/card.service';
import { PlayerService } from '../game/player.service';
import { ShardCardDeck, ShardCard, MaterialType, DestinationCardDeck, DestinationCard } from '@core/model/cards';
import { UserService } from '@core/user.service';
import { RouteService } from '../game/route.service';
import { RouteName } from '@core/model/route';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { ClaimRouteCommand } from '@core/game-commands';

@Injectable({
  providedIn: 'root'
})
export class ConsoleCommandService {

  constructor(private playerService: PlayerService, private cardService: CardService, private myUser: UserService, private routeService:RouteService, private serverProxyService:ServerProxyService) {
   }

  public parseCommand(message: string){
    var first = message.split(" ")[0];
    switch(first){
      case "/rainbowroad":{
        this.rainbowCommand();
        break;
      }
      case "/allyourbasesaremine":{
        this.allYourBasesCommand();
        break;
      }
      case "/claim":{
        this.claimCommand(message);
        break;
      }
      case "/givethemtrainsorgivemedeath":{
        this.giveThemTrains();
        break;
      }
      case"/myturn":{
        this.changeTurn(message);
        break;
      }
      case"/heartinthecards":{
        this.killDecks();
        break;
      }
      case"/finaldestination":{
        this.finalDestination();
        break;
      }
      case"/newroad":{
        this.newRoad(message);
        break;
      }
      default:{
        break;
      }
    }

  }

  private rainbowCommand(){
    this.cardService.faceUpShardCards = new ShardCardDeck([new ShardCard({"type":"INFINITY_GAUNTLET"}), 
        new ShardCard({"type":"INFINITY_GAUNTLET"}), new ShardCard({"type":"INFINITY_GAUNTLET"}), 
        new ShardCard({"type":"INFINITY_GAUNTLET"}), new ShardCard({"type":"INFINITY_GAUNTLET"})]);
  }

  private allYourBasesCommand(){
    this.playerService.players.forEach(function (value){
      value.points = -100;
    });
    this.playerService.playersById.get(this.myUser.user$.value.getUserId()).points = 1000;
  }

  private claimCommand(message: string){
      var player = message.split(" ")[1];
      var route = message.split(" ")[2];
      var userId;
      this.playerService.players.forEach(function (value){
        if(value.username === player){
          userId = value.userId;
        }
      })
      if(userId != null){
        this.routeService.updateOwnership(route as RouteName, userId);
      }
  }

  private changeTurn(message){
    var player = message.split(" ")[1];
    var userId;
    this.playerService.players.forEach(function (value){
      if(value.username === player){
        userId = value.userId;
      }
    })
    if(userId != null){
    this.playerService.activePlayerId = userId;
    }
  }

  private giveThemTrains(){
    this.playerService.players.forEach(function (value){
      value.numRemainingTrains = 100;
    });
    this.playerService.playersById.get(this.myUser.user$.value.getUserId()).numRemainingTrains = 10;
  }

  private killDecks(){
    this.cardService.destCardDeckSize = 0;
    this.cardService.shardCardDeckSize = 0;
    this.cardService.shardCardDiscardSize = 47;
    this.playerService.players.forEach(function (value){
      value.numDestinationCards = 30;
      value.numTrainCards = 0;
    });
    for(var i = 0; i <100; i++){
    this.cardService.playerTrainCards.cards.push(new ShardCard({"type":"INFINITY_GAUNTLET"}));
    }

  }

  private finalDestination(){
    this.cardService.playerDestCards = new DestinationCardDeck([]);
  }

  private newRoad(message: string){
    var cityOne = message.split(" ")[1];
    var cityTwo = message.split(" ")[2];
    var points = message.split(" ")[3];
    var ends = [cityOne, cityTwo];
    this.cardService.playerDestCards.cards.push(new DestinationCard({"cities": ends, "points": points}));
  }





}
