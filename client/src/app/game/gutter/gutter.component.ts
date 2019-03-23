import { Component, OnInit } from '@angular/core';
import { DestinationCard, MaterialType, ShardCard} from '@core/model/cards';
import { City } from '@core/model/route';
import { CardService } from '../card.service';
import { UserService } from '@core/user.service';
import { PlayerService } from '../player.service';
import { GamePlayer } from '@core/model/game-player';
import { StyleColor } from '@core/model/color.enum';


@Component({
  selector: 'app-gutter',
  templateUrl: './gutter.component.html',
  styleUrls: ['./gutter.component.scss']
})
export class GutterComponent implements OnInit {

  constructor(public cardService: CardService, public playerService: PlayerService) {
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

  ngOnInit() {
  }

  getCardImage(type: MaterialType): string {
    return ShardCard.getImage(type);
  }

  getRemainingShards(): number {
    if (this.playerService.myPlayer === undefined || this.playerService.myPlayer === null) {
      return 0;
    }

    return this.playerService.myPlayer.numRemainingTrains;
  }

  getPlayerColorStyle() {
    if (this.playerService.myPlayer === undefined || this.playerService.myPlayer === null) {
      return {'fill': '#' + StyleColor.YELLOW};
    }

    return {'fill': '#' + StyleColor[this.playerService.myPlayer.color]};
  }
}

