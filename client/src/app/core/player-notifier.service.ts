import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ShardCard } from './model/cards';

@Injectable({
  providedIn: 'root'
})
export class PlayerNotifierService {
  public playerNotification$: Subject<string> = new Subject<string>();
  public drawnCard$: Subject<ShardCard> = new Subject<ShardCard>();
  private notificationQueue: Array<string | ShardCard> = []
  private lastMsgComplete;

  constructor() { }

  public notifyPlayer(message: string) {
    this.notificationQueue.push(message);

    if (this.lastMsgComplete){
      this.showNext();
    }
  }

  public showCardDrawn(card: ShardCard) {
    this.notificationQueue.push(card);

    if (this.lastMsgComplete){
      this.showNext();
    }
  }

  // Register as after dismissed handler
  public showNext(){
    if(this.notificationQueue.length === 0){    // All messages shown
      this.lastMsgComplete = true;
      return;
    }

    this.lastMsgComplete = false;
    let nextMsg = this.notificationQueue.shift();
    if(nextMsg instanceof ShardCard){
      this.drawnCard$.next(nextMsg);
    } else if (typeof nextMsg === "string"){
      this.playerNotification$.next(nextMsg);
    }
    // Some other type?
  }
}
