import { Component, OnInit } from '@angular/core';
import { CardService } from '../card.service';
import { ShardCard } from '@core/model/cards';
import { MaterialType } from '@core/model/material-type.enum';


@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.scss']
})
export class BankComponent implements OnInit {

  constructor(public cardService: CardService) { }

  ngOnInit() {
  }

  public getCardImage(type: MaterialType): string {
    return ShardCard.getImage(type);
  }

  public faceUpShardCardClick(card: ShardCard) {
    this.cardService.drawFaceUpShardCard(card);
  }

  public shardCardDeckClick() {
    this.cardService.drawShardCardFromDeck();
  }

  public destCardDeckClick() {
    this.cardService.drawDestCardFromDeck();
  }

  public getTooltip(shardCard : ShardCard) : string {
    return ShardCard.getPrintName(shardCard.type);
  }

}

