import { Injectable } from '@angular/core';
import { timer, Subscription } from 'rxjs';
import { ServerProxyService } from './server-proxy.service';
import { ListGamesCommand } from './lobby-commands';

@Injectable({
  providedIn: 'root'
})
export class PollerService {
  constructor(private server: ServerProxyService) {
    this.startPolling();
  }
  public normalInterval = 500;
  public errorInterval = 15000;

  private timerNormal$ = timer(0, this.normalInterval);
  private timerError$ = timer(0, this.errorInterval);
  private timerSubscriptionNormal: Subscription;
  private timerSubscriptionError: Subscription;
  private failedResponseSubscription: Subscription;
  private lobbyMode = false;

  public ngOnDestroy() {
    this.stopPolling();
  }

  public setLobbyMode(lobbyMode: boolean) {
    this.lobbyMode = lobbyMode;
  }

  public startPolling() {
    if (this.failedResponseSubscription === undefined) {
      this.server.failedRequests$.subscribe({next: (val) => this.onFailedResponseChange(val)});
    }
    if (this.timerSubscriptionNormal === undefined) {
      this.timerSubscriptionNormal = this.timerNormal$.subscribe(_ => this.poll());
    }
  }

  public stopPolling() {
    this.timerSubscriptionNormal.unsubscribe();
    this.timerSubscriptionError.unsubscribe();
    this.timerSubscriptionNormal = undefined;
    this.timerSubscriptionError = undefined;
  }

  private startErrorPolling() {
    if (this.timerSubscriptionNormal !== undefined) {
      this.timerSubscriptionNormal.unsubscribe();
      this.timerSubscriptionNormal = undefined;
    }

    if (this.timerSubscriptionError === undefined) {
      this.timerSubscriptionError = this.timerError$.subscribe(_ => this.poll());
    }
  }

  private stopErrorPolling() {
    if (this.timerSubscriptionError !== undefined) {
      this.timerSubscriptionError.unsubscribe();
      this.timerSubscriptionError = undefined;
    }

    if (this.timerSubscriptionNormal === undefined) {
      this.timerSubscriptionNormal = this.timerNormal$.subscribe(_ => this.poll());
    }
  }

  private onFailedResponseChange(failedResponses: number) {
    if (failedResponses === 5) {  // After 5 failures start polling every 15 seconds
      this.startErrorPolling();
    } else if (failedResponses === 0) {   // Conection restored, resume higher frequency
     this.stopErrorPolling();
    }
  }

  private poll() {
    if (this.lobbyMode) {    // If in the lobby screen, send ListGamesCommand
      // use refresh game list command
      this.server.transmitCommand(new ListGamesCommand());
    } else {
      this.server.poll();
    }
  }

}
