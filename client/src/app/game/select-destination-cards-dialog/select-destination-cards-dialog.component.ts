import { Component,  Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DestinationCard } from '@core/model/cards';

@Component({
  selector: 'app-select-destination-cards-dialog',
  templateUrl: './select-destination-cards-dialog.component.html',
  styleUrls: ['./select-destination-cards-dialog.component.scss']
})
export class SelectDestinationCardsDialogComponent {
  private cards: {'card': DestinationCard, 'selected': boolean}[] = [];
  private numSelectedCards: number = 0;
  private minRequired: number;

  constructor(
    public dialogRef: MatDialogRef<SelectDestinationCardsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {'newCards': DestinationCard[], 'minRequired': number}) {
      data.newCards.forEach(card => {
        this.cards.push({'card': card, 'selected': false});
      });
      this.minRequired = data.minRequired;
    }

  onSelectClick(): void {
    // Call card service to discard cards
    this.dialogRef.close();
  }

  onCardClick(card: {'card': DestinationCard, 'selected': boolean}){
    card.selected = !card.selected;
    this.numSelectedCards = this.cards.filter(destCard => destCard.selected == true).length;
  }

}
