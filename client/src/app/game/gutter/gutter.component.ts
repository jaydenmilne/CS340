import { Component, OnInit } from '@angular/core';
import { MaterialType } from '@core/model/material-type.enum';
import { CardService } from '../card.service';
import { PlayerService } from '../player.service';
import { ShardCard } from '@core/model/cards';


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
    return ShardCard.getImageByType(type);
  }

  getRemainingShards(): number {
    if (this.playerService.myPlayer === undefined || this.playerService.myPlayer === null) {
      return 0;
    }

    return this.playerService.myPlayer.numRemainingTrains;
  }

  public getTooltip(type : MaterialType) : string {
    return ShardCard.getPrintName(type);
  }

}

