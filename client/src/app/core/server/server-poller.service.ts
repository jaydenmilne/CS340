import { Injectable } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { ServerConnectionService } from './server-connection.service';
import { ServerPollerState, NormalPollingState, ManualPollingState } from './server-poller-state';

@Injectable({
  providedIn: 'root'
})
export class ServerPollerService {
  public POLLING_INTERVAL_MS = 500;
  public DISCONNECTED_POLLING_INTERVAL_MS = 5000;

  private state: ServerPollerState;
  public manualPolling = false;
  // Timer that triggers polls
  private pollingTrigger$ = interval(this.POLLING_INTERVAL_MS);
  private sub: Subscription;

  constructor(public connection: ServerConnectionService) {

    this.setState(new NormalPollingState());
    this.connection.disconnectedFromServer$.subscribe( _ => this.onServerDisconnected());

  }

  public setState(newState) {
    if (this.state) {
      this.state.exit(this);
    }

    this.state = newState;
    this.state.enter(this);
  }

  public changeInterval(newInterval: number) {
    if (this.sub) {
      this.sub.unsubscribe();
    }

    if (newInterval == -1) {
      // Disable interval
      return;
    }

    this.pollingTrigger$ = interval(newInterval);
    this.sub = this.pollingTrigger$.subscribe( _ => this.poll());
  }

  /**
   * Increases polling interval, subscribes to listen for a successful tranmission
   */
  private onServerDisconnected() {
    this.state.onDisconnect(this);
  }

  /**
   * Does what it says on the tin
   */
  public poll() {
    this.connection.poll();
  }

  public handleKeypress(event : KeyboardEvent) {
    if (event.key == "m") {
      if (this.manualPolling) {
        console.log("Automatically polling...");
        this.setState(new NormalPollingState());
        this.manualPolling = false;
      } else {
        console.log("Manually polling...");
        this.setState(new ManualPollingState());
        this.manualPolling = true;
      }
    } else if (event.key == "p") {
      console.log("Polling...");
      this.poll();
    }
  }

}
