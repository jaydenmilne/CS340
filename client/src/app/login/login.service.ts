import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ServerProxyService } from '@core/server-proxy.service';
import { LoginCommand, RegisterCommand, LoginResult } from '@core/logincommands';
import { CommandRouterService } from '@core/command-router.service';

@Injectable({
  providedIn: 'root' // this makes the service a singleton
})
export class LoginService {

  constructor(private serverProxy: ServerProxyService,
              private commandRouter: CommandRouterService ) {
    this.commandRouter.loginResult$.subscribe(
      result => this.handleResult(result)
    );
  }

  private loginErrorSource = new Subject<string>();
  public loginError$ = this.loginErrorSource.asObservable();

  handleResult(loginResult: LoginResult) {
    if (loginResult.authToken !== '') {
      return; // wasn't a failure
    }

    // Signal the component
    this.loginErrorSource.next(loginResult.error);
  }

  public commenceLogin(username: string, password: string) {
    this.serverProxy.transmitCommand(new LoginCommand(username, password));
  }

}
