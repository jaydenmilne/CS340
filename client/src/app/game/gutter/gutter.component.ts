import { Component, OnInit } from '@angular/core';
import { DestinationCard, MaterialType, ShardCard} from '@core/model/cards';
import { City } from '@core/model/route';
import { CardService } from '../card.service';


@Component({
  selector: 'app-gutter',
  templateUrl: './gutter.component.html',
  styleUrls: ['./gutter.component.scss']
})
export class GutterComponent implements OnInit {

  constructor(private cardService: CardService) { }

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

  getCardImage(type: MaterialType): string{
    return ShardCard.getImage(type);
  }
  
}

