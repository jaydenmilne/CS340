import { Component,  Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DestCardSelectionDeck, DestinationCardDeck, DestCardSelectionPair } from '@core/model/cards';

@Component({
  selector: 'app-select-destination-cards-dialog',
  templateUrl: './select-destination-cards-dialog.component.html',
  styleUrls: ['./select-destination-cards-dialog.component.scss']
})
export class SelectDestinationCardsDialogComponent {
  private cards: DestCardSelectionDeck;
  public minSelected = false;
  constructor(
    public dialogRef: MatDialogRef<SelectDestinationCardsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SelectDestinationCardsData) {
      this.cards = new DestCardSelectionDeck([]);
      this.cards.fromCards(data.newCards.cards);
    }

  onSelectClick(): void {
    // Call card service to discard cards
    let result = new SelectDestinationCardsResult(this.cards.getSelected().toDeck(), this.cards.getDiscarded().toDeck());
    this.dialogRef.close(result);
  }

  onCardClick(cardPair: DestCardSelectionPair){
    this.cards.selectCard(cardPair);
    this.minSelected = this.cards.numSelected >= this.data.minRequired;
  }

}

export class SelectDestinationCardsData {
  constructor(public newCards: DestinationCardDeck, public minRequired: number){}
}

export class SelectDestinationCardsResult {
  constructor(public selectedCards: DestinationCardDeck, public discardedCards: DestinationCardDeck){}
}
