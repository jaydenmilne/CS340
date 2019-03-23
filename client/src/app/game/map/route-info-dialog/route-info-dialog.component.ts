import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DestinationCard, ShardCard } from '@core/model/cards';
import { typeToMaterial, Route } from '@core/model/route';
import { PlayerService } from '../../player.service';

@Component({
  selector: 'app-route-info-dialog',
  templateUrl: './route-info-dialog.component.html',
  styleUrls: ['./route-info-dialog.component.scss']
})
export class RouteInfoDialogComponent {
    constructor(
      private playerService: PlayerService,
      public dialogRef: MatDialogRef<RouteInfoDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: RouteInfoData) {
      }

    onClaimClick(): void {
      // Call card service to discard cards
      const result = new RouteInfoResult(true);
      this.dialogRef.close(result);
    }

    onCloseClick() {
      const result = new RouteInfoResult(false);
      this.dialogRef.close(result);
    }

    routeToDestCard(): DestinationCard {
      return new DestinationCard({'cities': this.data.route.cities, 'points': this.data.route.getPoints()});
    }

    getCardImage(card: ShardCard): string {
      return ShardCard.getImage(card.type);
    }

    getRouteTypeImage(): string {
      return ShardCard.getImage(typeToMaterial[this.data.route.type]);
    }


  getRouteOwnerName(): string {
    if (this.data.route.ownerId === -1) {
      return 'Available';
    }
    return this.playerService.playersById.get(this.data.route.ownerId).username;
  }
}

export class RouteInfoData {
  constructor(public route: Route, public canClaim: boolean) {}
}

export class RouteInfoResult {
  constructor(public claimRoute: boolean) {}
}
