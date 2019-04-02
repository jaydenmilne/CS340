import { Component, OnInit } from '@angular/core';
import { CardService } from '../card.service';
import { ShardCard } from '@core/model/cards';
import { MaterialType } from '@core/model/material-type.enum';
import { TurnService } from '../turn/turn.service';


@Component({
  selector: 'app-bank',
  templateUrl: './bank.component.html',
  styleUrls: ['./bank.component.scss']
})
export class BankComponent implements OnInit {

  constructor(public cardService: CardService, private turnService: TurnService) { }

  ngOnInit() {
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

  public showSkipButton() : boolean {
    return this.turnService.isMyTurn() && 
            (this.cardService.faceUpShardCards.size().valueOf() + this.cardService.shardCardDeckSize) < 2 &&
            this.cardService.destCardDeckSize == 0;
  }

  public onSkipClick() {
    this.turnService.skipTurn();
  }
}

