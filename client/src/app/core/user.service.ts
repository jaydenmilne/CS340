import { Injectable } from '@angular/core';
import { CommandRouterService } from './command-router.service';
import { LoginResult } from './login-commands';
import { User } from './model/user';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public user$ = new BehaviorSubject<User>(null);

  private onLoginResult(loginResult: LoginResult) {
    if (loginResult.error === '') { return; }
    this.user$.next(loginResult.user);
  }

  constructor(private commandRouter: CommandRouterService) {
    commandRouter.loginResult$.subscribe(loginResult => this.onLoginResult(loginResult));
  }
}
