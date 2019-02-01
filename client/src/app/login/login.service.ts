import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ServerProxyService } from '@core/server-proxy.service';
import { LoginCommand, RegisterCommand } from '@core/logincommands';

@Injectable({
  providedIn: 'root' // this makes the service a singleton
})
export class LoginService {

  constructor(private serverProxy : ServerProxyService ) { 
    console.log("LoginService is alive!");
  }

  private loginErrorSource = new Subject<string>();
  public loginError$ = this.loginErrorSource.asObservable();

  public commenceLogin(username : string, password: string) {
    console.log("starting login...");
    this.serverProxy.transmitCommand(new LoginCommand(username, password));
  }

}
