import { Component, OnInit } from '@angular/core';
import { DestinationCard, MaterialType, ShardCard} from '@core/model/cards';
import { City } from '@core/model/route';
import { CardService } from '../card.service';
import { UserService } from '@core/user.service';
import { PlayerService } from '../player.service';
import { GamePlayer } from '@core/model/game-player';


@Component({
  selector: 'app-gutter',
  templateUrl: './gutter.component.html',
  styleUrls: ['./gutter.component.scss']
})
export class GutterComponent implements OnInit {
  private myPlayer: number;

  constructor(private cardService: CardService, private userService: UserService, private playerService: PlayerService) {
    this.myPlayer = userService.user$.value.getUserId();
   }

  ngOnInit() {
  }

  shardTypes: MaterialType[] = [
    MaterialType.REALITY_SHARD,
    MaterialType.SOUL_SHARD,
    MaterialType.SPACE_SHARD,
    MaterialType.MIND_SHARD,
    MaterialType.POWER_SHARD,
    MaterialType.TIME_SHARD,
    MaterialType.VIBRANIUM,
    MaterialType.PALLADIUM,
    MaterialType.INFINITY_GAUNTLET
  ];

  private getCardImage(type: MaterialType): string{
    return ShardCard.getImage(type);
  }
  
  private getRemainingShards(): number{
    let gamePlayer: GamePlayer = this.playerService.playersById.get(this.myPlayer);

    if (gamePlayer === undefined){
      return 0;
    }

    return gamePlayer.numRemainingTrains;
  }

}

