import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ServerProxyService } from '@core/server-proxy.service';
import { LoginCommand, RegisterCommand, LoginResult } from '@core/login-commands';
import { CommandRouterService } from '@core/command-router.service';
import { Router } from '@angular/router';
import { UserService } from '@core/user.service';

@Injectable({
  providedIn: 'root' // this makes the service a singleton
})
export class LoginService {

  constructor(private serverProxy: ServerProxyService,
              private commandRouter: CommandRouterService,
              private router: Router,
              private userService: UserService ) {
    this.commandRouter.loginResult$.subscribe(
      result => this.handleResult(result)
    );
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
    this.serverProxy.transmitRegisterCommand(new LoginCommand(username, password));
  }

  /**
   * Begins the registration process
   */
  public commenceRegister(username: string, password: string) {
    this.serverProxy.transmitRegisterCommand(new RegisterCommand(username, password));
  }

}
