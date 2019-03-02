import { Injectable } from '@angular/core';
import { Command } from '@core/command';
import { Subject } from 'rxjs';
import { ServerConnectionService } from './server-connection.service';

@Injectable({
  providedIn: 'root'
})
export class ServerProxyService {

  public incomingCmd$ = new Subject<Command>();

  /**
   * Sends a command to the server and parses commands it receives in response.
   * @param command Command-like object to transmit to the server
   */
  public sendCommand(command: Command) {
    this.serverConnection.transmitCommand(command);
  }

  constructor(private serverConnection : ServerConnectionService) {
    serverConnection.incomingCmd$.subscribe(cmd => {
      this.incomingCmd$.next(cmd);
    });
  }
}
