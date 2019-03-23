import { Component, OnInit } from '@angular/core';
import { CardService } from '../card.service';
import { MaterialType, ShardCard } from '@core/model/cards';


@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.scss']
})
export class BankComponent implements OnInit {

  constructor(public cardService: CardService) { }

  ngOnInit() {
  }

  private getCardImage(type: MaterialType): string {
    return ShardCard.getImage(type);
  }

}

