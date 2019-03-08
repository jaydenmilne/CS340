import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.scss']
})
export class BankComponent implements OnInit {

  constructor() { }

  private shardCards = [
    new shardCount('Reality Stone', 1, 'reality_stone.svg'),
    new shardCount('Soul Stone', 2, 'soul_stone.svg'),
    new shardCount('Time Stone', 3, 'time_stone.svg'),
    new shardCount('Space Stone', 4, 'space_stone.svg'),
    new shardCount('Power Stone', 5, 'power_stone.svg'),
  ]

  ngOnInit() {
  }

  

}
class shardCount {
  constructor(public type: string, public count: number, public img: string){}
}
