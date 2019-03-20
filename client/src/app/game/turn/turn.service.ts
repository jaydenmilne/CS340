import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TurnService {
  constructor() { }

  isMyTurn(): boolean { return true; }
  canDrawShards(): boolean { return true; }
  canDrawWild(): boolean { return true; }
  canDrawDestinations(): boolean { return true; }
  canClaimRoutes(): boolean { return true; }
  setNextState(ITurnState) { }

}
