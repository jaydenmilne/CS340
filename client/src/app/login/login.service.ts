import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { LoginCommand, RegisterCommand, LoginResult } from '@core/login-commands';
import { CommandRouterService } from '@core/command-router.service';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { LoginState } from '@core/server/server-connection-state';
import { UserService } from '@core/user.service';
import { ServerPollerService } from '@core/server/server-poller.service';

@Injectable({
  providedIn: 'root' // this makes the service a singleton
})
export class LoginService {

  constructor(private serverProxy: ServerProxyService,
              private commandRouter: CommandRouterService,
              private serverConnection: ServerConnectionService,
              private UserService: UserService,
              private poller: ServerPollerService) {
    this.commandRouter.loginResult$.subscribe(
      result => this.handleResult(result)
    );

    // Change state to login state
    this.serverConnection.changeState(new LoginState(this.serverConnection));
  }

  public loginError$ = new Subject<string>();

  /**
   * Parses a loginResult command and either routes away from login page
   * or displays a login error.
   * @param loginResult Client command to parse
   */
  handleResult(loginResult: LoginResult) {
    if (loginResult.user !== null) {
      // Login/Register worked
      this.serverConnection.registerAuthToken(loginResult.user.getAuthToken());
      // User service will route to the lobby
      return;
    }

    // Signal the component
    this.loginError$.next(loginResult.error);
  }

  /**
   * Begins the login process
   */
  public commenceLogin(username: string, password: string) {
    this.serverProxy.executeCommand(new LoginCommand(username, password));
  }

  /**
   * Begins the registration process
   */
  public commenceRegister(username: string, password: string) {
    this.serverProxy.executeCommand(new RegisterCommand(username, password));
  }

}
