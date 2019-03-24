import { Injectable } from '@angular/core';
import { CommandRouterService } from './command-router.service';
import { LoginResult } from './login-commands';
import { User } from './model/user';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public isLoggedIn = false;
  public user$ = new BehaviorSubject<User>(null);

  private onLoginResult(loginResult: LoginResult) {
    if (loginResult.error === '') {
      // Login worked, signal next user
      this.user$.next(loginResult.user);
      return;
    }

  }

  private onUser(user: User) {
    if (user != null) {
      this.isLoggedIn = true;
      this.router.navigate(['/lobby']);
    }
  }

  constructor(private commandRouter: CommandRouterService,
              private router: Router) {
    this.commandRouter.loginResult$.subscribe(loginResult => this.onLoginResult(loginResult));
    this.user$.subscribe(user => this.onUser(user));
  }
}
