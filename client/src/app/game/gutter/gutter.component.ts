import { Component, OnInit } from '@angular/core';
import { DestinationCard, City } from '@core/model/cards';


@Component({
  selector: 'app-gutter',
  templateUrl: './gutter.component.html',
  styleUrls: ['./gutter.component.scss']
})
export class GutterComponent implements OnInit {

  constructor() { }

  private shardCards = [
    new shardCount('Reality Stone', 1, 'reality_stone.svg'),
    new shardCount('Soul Stone', 2, 'soul_stone.svg'),
    new shardCount('Time Stone', 3, 'time_stone.svg'),
    new shardCount('Space Stone', 4, 'space_stone.svg'),
    new shardCount('Power Stone', 5, 'power_stone.svg'),
    new shardCount('Mind Stone', 6, 'mind_stone.svg'),
    new shardCount('Palladium', 7, 'palladium.svg'),
    new shardCount('Vibranium', 8, 'vibranium.svg'),
    new shardCount('Infinity Gauntlet', 9, 'gauntlet.svg'),
  ]


  private destCards = [
    new DestinationCard([City.XANDAR, City.ASGARD], 5),
    new DestinationCard([City.CHITAURI_SANCTUARY, City.HONG_KONG_SANCTUM], 10),
    new DestinationCard([City.GALACTUS, City.YOTUNHEIM], 15),
  ]

  ngOnInit() {
  }

}

class shardCount {
  constructor(public type: string, public count: number, public img: string){}
}

