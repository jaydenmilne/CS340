import { ViewChild, ElementRef, Component, OnInit, OnDestroy } from '@angular/core';
import { RouteService } from '../route.service';
import { Route } from '@core/model/route';
import { RouteName } from '@core/model/route-name.enum';
import { PlayerService } from '../player.service';
import { ErrorNotifierService } from '@core/error-notifier.service';
import { ShardCardDeck } from '@core/model/cards';
import { CardService } from '../card.service';
import { RouteInfoDialogComponent, RouteInfoData } from './route-info-dialog/route-info-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ClaimRoutesDialogComponent, ClaimRouteData } from '../claim-routes-dialog/claim-routes-dialog.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  constructor(public routeService: RouteService, private cardService: CardService , private playerService: PlayerService, private errorNotifier: ErrorNotifierService,  public dialog: MatDialog) {
      this.subscriptions.push(this.routeService.routeOwnershipChanged$.subscribe( route => this.onRouteOwnershipChange(route)));
     }

  @ViewChild('mapSvg')
  mapSvg: ElementRef;

  ngOnInit() {
    if (this.mapSvg === undefined) {
      console.error('Map not found!');
    }

    this.mapSvg.nativeElement.addEventListener('click', this.onSvgClick.bind(this));
  }

  ngOnDestroy(){
    this.subscriptions.forEach(sub => { sub.unsubscribe() });
  }

  private onSvgClick(event: any) {
    if (event.target) {
      // Walk up the path to try and find the group that contains the path, shield, and line
      for (let i = 0; i < event.path.length; ++i) {
        const node = event.path[i];
        if (node.id === 'mapSvg') {
          // Isn't a route
          return;
        }
        if (this.routeService.routes.has(node.id)) {
          // alert(node.id);
          this.openRouteInfo(node.id);
        }
      }
    }
  }

  private onRouteOwnershipChange(route: Route) {
    const player = this.playerService.playersById.get(route.ownerId);

    if (route.ownerId === -1) {
      console.debug('Ownership of a route reverted back to no-one? MapComponent doesn\'t appreciate this');
    }

    if (!player) {
      this.errorNotifier.notifyHeading('MapComponent::onRouteOwnershipChange', 'Got a player that doesn\'t exist! playerid = ' + route.ownerId);
    }

    const routeLine = this.mapSvg.nativeElement.getElementById(route.routeName + '-fg');
    if (!routeLine) {
      this.errorNotifier.notifyHeading('MapComponent::onRouteOwnershipChange', 'Unable to find the -fg line for route ' + route.routeName);
      return;
    }
    routeLine.style.stroke = player.getStyleColor();
  }

  private openRouteInfo(routeName: RouteName) {
    // Get route by Id
    const route: Route = this.routeService.getRouteById(routeName);
    const hand: ShardCardDeck = this.cardService.playerTrainCards;
    // Determine if we can claim it
    const canClaim = this.routeService.claimRoutePossible(route, hand);
    // Open route info modal
    const routeInfoBox = this.dialog.open(RouteInfoDialogComponent, {
      width: '350px',
      data: new RouteInfoData(route, canClaim),
      disableClose: false
    });

    routeInfoBox.afterClosed().subscribe(routeInfoResult => {
      // On close, determine if we need to open claim route
      if (routeInfoResult !== undefined && routeInfoResult.claimRoute) {
        this.openClaimRoute(route, hand);
      }
    });
  }

  private openClaimRoute(route: Route, hand: ShardCardDeck) {
    // Open claim route modal
    const claimRouteBox = this.dialog.open(ClaimRoutesDialogComponent, {
      width: '350px',
      data: new ClaimRouteData(route, hand),
      disableClose: true
    });

    claimRouteBox.afterClosed().subscribe(claimRouteResult => {
    // On close, determine if we need to claim route
      if (claimRouteResult.routeClaimed && this.routeService.claimRouteValid(route, claimRouteResult.usedCards)) {
        // Call claim route
        this.routeService.claimRoute(route, claimRouteResult.usedCards);
      }
    });
  }
}
