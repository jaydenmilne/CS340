import { Component, Inject } from '@angular/core';
import { Route, typeToMaterial } from '@core/model/route';
import { ShardCard, DestinationCard, MaterialType } from '@core/model/cards';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-claim-routes-dialog',
  templateUrl: './claim-routes-dialog.component.html',
  styleUrls: ['./claim-routes-dialog.component.scss']
})
export class ClaimRoutesDialogComponent {
  private cards: {'card': ShardCard, 'selected': boolean}[] = [];

  constructor(
    public dialogRef: MatDialogRef<ClaimRoutesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ClaimRouteData) {
      data.hand.forEach(card => {
        this.cards.push({'card': card, 'selected': false});
      });
    }

  onSelectClick(): void {
    // Call card service to discard cards
    let result = new ClaimRouteResult(true, []);

    this.cards.forEach(cardPair => {
      if (cardPair.selected){
        result.usedCards.push(cardPair.card);
      }
    })

    this.dialogRef.close(result);
  }

  onCancelClick(){
    let result = new ClaimRouteResult(false, []);
    this.dialogRef.close(result);
  }

  onCardClick(card: {'card': ShardCard, 'selected': boolean}){
    card.selected = !card.selected;
  }

  routeToDestCard(): DestinationCard {
    return new DestinationCard({"cities": this.data.route.cities, "points": this.data.route.getPoints()});
  }

  getCardImage(card: ShardCard): string{
    return ShardCard.getImage(card.type);
  }

  getRouteTypeImage(): string{
    return ShardCard.getImage(typeToMaterial[this.data.route.type]);
  }
}

export class ClaimRouteData {
  constructor(public route: Route, public hand: ShardCard[]){}
}

export class ClaimRouteResult {
  constructor(public routeClaimed: boolean, public usedCards: ShardCard[]){}
}