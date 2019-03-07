import { Component, OnInit, Input, ViewChild, ElementRef, Renderer } from '@angular/core';
import { DestinationCard } from '@core/model/cards';

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



}
