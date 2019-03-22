import { ViewChild, ElementRef, Component, OnInit } from '@angular/core';
import { RouteService } from '../route.service';
import { Route } from '@core/model/route';
import { PlayerService } from '../player.service';
import { ErrorNotifierService } from '@core/error-notifier.service';
import { StyleColor } from '@core/model/color.enum';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  constructor(public routeService: RouteService,
    private playerService: PlayerService,
    private errorNotifier: ErrorNotifierService) { }

  @ViewChild('mapSvg')
  mapSvg: ElementRef;

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
          this.routeService.updateOwnership(node.id, this.playerService.players[0].userId);
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

    const color = '#' + StyleColor[player.color];

    const routeLine = this.mapSvg.nativeElement.getElementById(route.routeName + '-fg');
    if (!routeLine) {
      this.errorNotifier.notifyHeading('MapComponent::onRouteOwnershipChange', 'Unable to find the -fg line for route ' + route.routeName);
      return;
    }
    routeLine.style.stroke = color;
  }

  ngOnInit() {
    if (this.mapSvg === undefined) {
      console.error('Map not found!');
    }

    this.mapSvg.nativeElement.addEventListener('click', this.onSvgClick.bind(this));
    this.routeService.routeOwnershipChanged$.subscribe( route => this.onRouteOwnershipChange(route));
  }

}
