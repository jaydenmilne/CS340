import { Component, OnInit, Input } from '@angular/core';
import { DestinationCard } from '@core/model/cards';
import { cityPrintNames } from '@core/model/route';
import { City } from "@core/model/city.enum";

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

  getPrintName(city: City): string {
    const str: string = cityPrintNames[city];
    return str;
  }

}
