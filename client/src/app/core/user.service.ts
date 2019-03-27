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

  // Keeps track of the rejoining status. If -1 the user was in no game previously, else they are
  // rejoining a game.
  private gameid = -1;

  private onLoginResult(loginResult: LoginResult) {
    if (loginResult.error === '') {
      this.gameid = loginResult.game;
      this.user$.next(loginResult.user);
    }

  }

  private onUser(user: User) {
    if (user != null && this.gameid == -1) {
      this.isLoggedIn = true;
      this.router.navigate(['/lobby']);
    } else {
      this.router.navigate(['/game/' + this.gameid]);
    }
  }

  constructor(private commandRouter: CommandRouterService,
              private router: Router) {
    this.commandRouter.loginResult$.subscribe(loginResult => this.onLoginResult(loginResult));
    this.user$.subscribe(user => this.onUser(user));
  }
}
