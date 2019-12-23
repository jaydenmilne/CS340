import { Component,  Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DestCardSelectionDeck, DestinationCardDeck, DestCardSelectionPair } from '@core/model/cards';
import { TurnService } from '../turn/turn.service';

@Component({
  selector: 'app-select-destination-cards-dialog',
  templateUrl: './select-destination-cards-dialog.component.html',
  styleUrls: ['./select-destination-cards-dialog.component.scss']
})
export class SelectDestinationCardsDialogComponent {
  public cards: DestCardSelectionDeck;
  public minSelected = false;
  constructor(
    private turnService: TurnService,
    public dialogRef: MatDialogRef<SelectDestinationCardsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SelectDestinationCardsData) {
      this.cards = new DestCardSelectionDeck([]);
      this.cards.fromCards(data.newCards.cards);
    }

  onSelectClick(): void {
    // Call card service to discard cards
    const result = new SelectDestinationCardsResult(this.cards.getSelected().toDeck(), this.cards.getDiscarded().toDeck());
    this.dialogRef.close(result);

    // If it isn't the initial dest card draw, alert the TurnService so that
    // the toast displays.
    if (this.turnService.isMyTurn()) {
      this.turnService.onDrawDestCard();
    }
  }

  onCardClick(cardPair: DestCardSelectionPair) {
    this.cards.selectCard(cardPair);
    this.minSelected = this.cards.numSelected >= this.data.minRequired;
  }

}

export class SelectDestinationCardsData {
  constructor(public newCards: DestinationCardDeck, public minRequired: number) {}
}

export class SelectDestinationCardsResult {
  constructor(public selectedCards: DestinationCardDeck, public discardedCards: DestinationCardDeck) {}
}
