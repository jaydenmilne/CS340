import { Injectable } from '@angular/core';
import { ServerProxyService } from '@core/server-proxy.service';
import { Command } from '@core/command';
import { Subject } from 'rxjs';
import { LoginResult } from './logincommands';
import { ErrorNotifierService, ErrorMessage } from './error-notifier.service';

@Injectable({
  providedIn: 'root'
})
export class CommandRouterService {

  constructor(private serverProxy: ServerProxyService,
              private errorNotifier: ErrorNotifierService) {
    this.serverProxy.incomingCmd$.subscribe(command => this.handleIncomingCommand(command));
  }

  // ------------------------------
  // OBSERVABLES (one for every client command type)

  private loginResultSrc = new Subject<LoginResult>();
  public loginResult$ = this.loginResultSrc.asObservable();

  handleIncomingCommand(cmd: Command) {
    switch (cmd.command) {
    case 'loginResult':
      this.loginResultSrc.next(cmd as LoginResult);
      break;
    default:
      const msg = new ErrorMessage(
        'Got an unknown command type from the server',
        'type: ' + cmd.command,
        'Command info: ' + JSON.stringify(cmd), true);

      this.errorNotifier.notifyMessage(msg);
      break;
    }
  }
}
