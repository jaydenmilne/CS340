import { ServerPollerService } from './server-poller.service';
import { Subscription } from 'rxjs';

export class ServerPollerState {
  public onDisconnect(poller: ServerPollerService) {

  }

  public enter(poller: ServerPollerService) {

  }

  public exit(poller: ServerPollerService) {
    // Unsubscribe
    poller.changeInterval(-1);
  }
}

export class NormalPollingState extends ServerPollerState {
  public enter(poller: ServerPollerService) {
    poller.changeInterval(poller.POLLING_INTERVAL_MS);
  }

  public onDisconnect(poller: ServerPollerService) {
    poller.setState(new ErrorPollingState());
  }
}

export class ErrorPollingState extends ServerPollerState {
  private sub: Subscription;

  public enter(poller: ServerPollerService) {
    poller.changeInterval(poller.DISCONNECTED_POLLING_INTERVAL_MS);
    this.sub = poller.connection.transmissionOk$.subscribe( _ => this.onServerConnected(poller));
  }

  /**
   * Decreases the polling interval
   */
  private onServerConnected(poller: ServerPollerService) {
    this.sub.unsubscribe();
    poller.setState(new NormalPollingState());
  }

}

export class ManualPollingState extends ServerPollerState {
  public enter(poller: ServerPollerService) {
    poller.changeInterval(-1);
  }
}
