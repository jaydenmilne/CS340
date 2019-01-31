import { Injectable } from '@angular/core';
import { Subject, Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root' // this makes the service a singleton
})
export class LoginService {

  constructor() { 
    console.log("LoginService is alive!");
  }

  private loginErrorSource = new Subject<string>();
  public loginError$ = this.loginErrorSource.asObservable();

  public commenceLogin(username : string, password: string) {
    console.log("starting login...");
    this.loginErrorSource.next("The username is incorrect");
  }

}
