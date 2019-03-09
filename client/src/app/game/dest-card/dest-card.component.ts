import { Component, OnInit, Input } from '@angular/core';
import { DestinationCard } from '@core/model/cards';
import { City, cityPrintNames } from '@core/model/route';

@Component({
  selector: 'app-dest-card',
  templateUrl: './dest-card.component.html',
  styleUrls: ['./dest-card.component.scss']
})
export class DestCardComponent implements OnInit {
  @Input() route: DestinationCard;
  @Input() selected: boolean;
  
  constructor() { 
    }

  ngOnInit() {
  }

  getPrintName(city: City): string{
    let str: string = cityPrintNames[city];
    return str;
  }

}
