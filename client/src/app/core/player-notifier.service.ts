import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ShardCard } from './model/cards';

@Injectable({
  providedIn: 'root'
})
export class PlayerNotifierService {
  public playerNotification: Subject<string> = new Subject<string>();
  public drawnCard$: Subject<ShardCard> = new Subject<ShardCard>();

  constructor() { }

  public notifyPlayer(message: string) {
    this.playerNotification.next(message);
  }

  public showCardDrawn(card: ShardCard) {
    this.drawnCard$.next(card);
  }
}
