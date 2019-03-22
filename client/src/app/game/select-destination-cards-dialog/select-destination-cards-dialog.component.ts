import { Component,  Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DestinationCard } from '@core/model/cards';

@Component({
  selector: 'app-select-destination-cards-dialog',
  templateUrl: './select-destination-cards-dialog.component.html',
  styleUrls: ['./select-destination-cards-dialog.component.scss']
})
export class SelectDestinationCardsDialogComponent {
  public cards: {'card': DestinationCard, 'selected': boolean}[] = [];
  public numSelectedCards: number = 0;

  constructor(
    public dialogRef: MatDialogRef<SelectDestinationCardsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SelectDestinationCardsData) {
      data.newCards.forEach(card => {
        this.cards.push({'card': card, 'selected': false});
      });
    }

  onSelectClick(): void {
    // Call card service to discard cards
    let result = new SelectDestinationCardsResult([], []);

    this.cards.forEach(cardPair => {
      if (cardPair.selected){
        result.selectedCards.push(cardPair.card);
      }
      else{
        result.discardedCards.push(cardPair.card);
      }
    })

    this.dialogRef.close(result);
  }

  onCardClick(card: {'card': DestinationCard, 'selected': boolean}){
    card.selected = !card.selected;
    this.numSelectedCards = this.cards.filter(destCard => destCard.selected === true).length;
  }

}

export class SelectDestinationCardsData {
  constructor(public newCards: DestinationCard[], public minRequired: number){}
}

export class SelectDestinationCardsResult {
  constructor(public selectedCards: DestinationCard[], public discardedCards: DestinationCard[]){}
}