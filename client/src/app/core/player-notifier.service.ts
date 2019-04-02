import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ShardCard } from './model/cards';

@Injectable({
  providedIn: 'root'
})
export class PlayerNotifierService {
  public playerNotification$: Subject<IPlayerNotification> = new Subject<IPlayerNotification>();
  private notificationQueue: Array<IPlayerNotification> = []
  private lastMsgComplete = true;

  constructor() { }

  public notifyPlayer(message: string, displayTime: number = 2500) {
    this.notificationQueue.push(new TextNotification(message, displayTime));

    if (this.lastMsgComplete){
      this.showNext();
    }
  }

  public showCardDrawn(card: ShardCard, displayTime: number = 1500) {
    this.notificationQueue.push(new ShardNotification(card, displayTime));

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
    this.playerNotification$.next(this.notificationQueue.shift());
  }

  public clearData(){
    this.notificationQueue = [];
    this.lastMsgComplete = true;
  }
}

export interface IPlayerNotification{
  msg;
  displayTime: number; 
}

export class ShardNotification implements IPlayerNotification{
  constructor(public msg: ShardCard, public displayTime: number = 1500){}
}

export class TextNotification implements IPlayerNotification{
  constructor(public msg: string, public displayTime: number = 2500){}
}