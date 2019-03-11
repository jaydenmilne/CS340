import { Injectable } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { ServerConnectionService } from './server-connection.service';
import { ServerPollerState, NormalPollingState, ManualPollingState } from './server-poller-state';

/**
 * ServerPollingService
 * Periodically polls the server, behaves differently when disconnected, can be run manually.
 */
@Injectable({
  providedIn: 'root'
})
export class ServerPollerService {
  public POLLING_INTERVAL_MS = 500;
  public DISCONNECTED_POLLING_INTERVAL_MS = 5000;

  private state: ServerPollerState;
  // Public so that the state classes can have access
  public manualPolling = false;
  // Timer that triggers polls
  private pollingTrigger$ = interval(this.POLLING_INTERVAL_MS);
  private sub: Subscription;
  
  /**
   * Preconditions: ServerConnectionService running
   * Postcontitions: State will be "NormalPollingState"
   * @param connection 
   */
  constructor(public connection: ServerConnectionService) {

    this.setState(new NormalPollingState());
    this.connection.disconnectedFromServer$.subscribe( _ => this.onServerDisconnected());

  }

  /**
   * Preconditions: None
   * Postconditions: State will be newState, old state will be properly exited
   * @param newState 
   */
  public setState(newState) {
    if (this.state) {
      this.state.exit(this);
    }

    this.state = newState;
    this.state.enter(this);
  }

  /**
   * Preconditions: None
   * Postconditions: this.sub will be the new subscription to the timer
   *                 this.pollingTrigger$ will the new interval timer
   *                 this.poll() will be called when this.pollingTrigger$ emits event
   * @param newInterval 
   */
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
   * Preconditions: In a defined state
   * Postconditions: Normal Polling -> Error Polling
   *                 Error Polling -> no state change
   *                 Manual Polling -> no state change
   */
  private onServerDisconnected() {
    this.state.onDisconnect(this);
  }

  /**
   * Does what it says on the tin
   * Preconditions: In a defined state
   * Postconditions: In same state
   */
  public poll() {
    this.connection.poll();
  }

  /**
   * Listener to disable shortcut keys
   * Preconditions: None
   * Postconditions: if event.key is m and manually polling, NormalPollingState
   *                 if event.key is m and automatically polling, ManualPollingState
   *                 if event.key is p, a poll will be sent.
   * @param event 
   */
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
