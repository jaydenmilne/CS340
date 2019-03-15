import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlayerNotifierService {
  public playerNotification: Subject<string> = new Subject<string>();

  constructor() { }

  public notifyPlayer(message: string){
    this.playerNotification.next(message);
  }

}
