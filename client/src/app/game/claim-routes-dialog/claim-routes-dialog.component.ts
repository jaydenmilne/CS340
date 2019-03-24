import { Component, Inject } from '@angular/core';
import { Route, typeToMaterial } from '@core/model/route';
import { ShardCard, ShardCardDeck, ShardCardSelectionDeck, ShardCardSelectionPair } from '@core/model/cards';
import { MaterialType } from "@core/model/material-type.enum";
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RouteService } from '../route.service';

@Component({
  selector: 'app-claim-routes-dialog',
  templateUrl: './claim-routes-dialog.component.html',
  styleUrls: ['./claim-routes-dialog.component.scss']
})
export class ClaimRoutesDialogComponent {
  private cards: ShardCardSelectionDeck;
  private useableCards: ShardCardSelectionDeck;
  public claimValid: boolean = false;

  constructor(private routeService: RouteService,
    public dialogRef: MatDialogRef<ClaimRoutesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ClaimRouteData) {
      this.cards = new ShardCardSelectionDeck([]);
      this.cards.fromCards(this.data.hand.cards);
      this.filterUsableCards();
    }

  onClaimClick(): void {
    // Call card service to discard cards
    const result = new ClaimRouteResult(true, this.cards.getSelected().toDeck());
    this.dialogRef.close(result);
  }

  onCancelClick() {
    const result = new ClaimRouteResult(false, new ShardCardDeck([]));
    this.dialogRef.close(result);
  }

  onCardClick(card: ShardCardSelectionPair) {
    this.cards.selectCard(card);
    this.filterUsableCards();
    this.claimValid = this.routeService.claimRouteValid(this.data.route, this.cards.getSelected().toDeck());
  }

  private filterUsableCards() {
    if (this.cards.getSelectedType() === MaterialType.ANY) {
      this.useableCards = this.cards.filterOnType(this.data.route.getClaimableTypes());
    } else {
      this.useableCards = this.cards.filterOnSelectedType(true);
    }
  }

  getCardImage(card: ShardCard): string {
    return ShardCard.getImage(card.type);
  }

  getRouteTypeImage(): string {
    return ShardCard.getImage(typeToMaterial[this.data.route.type]);
  }
}

export class ClaimRouteData {
  constructor(public route: Route, public hand: ShardCardDeck) {}
}

export class ClaimRouteResult {
  constructor(public routeClaimed: boolean, public usedCards: ShardCardDeck) {}
}
