import { Injectable } from '@angular/core';
import { Command, CommandArray, ICommandArray } from './command';
import { isDevMode } from '@angular/core';
import { HttpClient, HttpResponse, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { ErrorNotifierService } from './error-notifier.service';
import { Subject, BehaviorSubject } from 'rxjs';
import { UserService } from './user.service';
import { LoginResult } from './login-commands';
import { ListGamesCommand } from './lobby-commands';

@Injectable({
  providedIn: 'root'
})

export class ServerProxyService {

  private commandEndpoint = '/command';
  private registerEndpoint = '/register';
  private authToken = '';

  public failedRequests$ = new BehaviorSubject <number>(0);
  public incomingCmd$ = new Subject<Command>();

  /**
   * Gets the correct url depending on development mode or not
   */
  private getServerUrl(): string {
    if (isDevMode()) { return 'http://127.0.0.1:4300'; } else { return 'api.marylou.ga'; }
  }

  /**
   * Returns the entire URL that we should be using (debug / production)
   */
  private getUrl(): string {
    return this.getServerUrl() + this.commandEndpoint;
  }

  /**
   * When an HTTP error occurs, handle it and notify the user
   * @param error the error to parse
   */
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      this.errorService.notifyServerCommError(error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);

      this.errorService.notifyHttpError(error.status, error.message);
    }
  }

    /**
   * When an HTTP error occurs, handle it and notify the user
   * @param error the error to parse
   */
  private handleFailedPoll(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      this.failedRequests$.next(this.failedRequests$.value + 1);
      if (this.failedRequests$.value == 5){  // Notify user after 5 failed polls
        this.errorService.notifyServerCommError(error.error.message);
      }
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);

      this.errorService.notifyHttpError(error.status, error.error.message);
    }
  }

  /**
   * Parses the commands out of a response from the server
   * @param result response to parse
   */
  private handleReponse(result: HttpResponse<CommandArray>) {
    const commands: CommandArray = result.body as CommandArray;
    this.failedRequests$.next(0);

    // Dispatch every command in the received array
    for (const command of commands.commands) {
      this.incomingCmd$.next(command);
    }
  }

  /**
   * Sends a command to the server and parses commands it receives in response.
   * @param command Command-like object to transmit to the server
   */
  public transmitCommand(command: Command) {
    this.sendCommandToEndpoint(command, this.getServerUrl() + this.commandEndpoint);
  }

  /**
   * Sends a command to the server and parses commands it receives in response.
   * @param command Command-like object to transmit to the server
   */
  public transmitRegisterCommand(command: Command) {
    this.sendCommandToEndpoint(command, this.getServerUrl() + this.registerEndpoint);
  }

  /**
   * Sends a command to given endpoint
   * @param command command like object
   * @param url where to send it
   */
  private sendCommandToEndpoint(command: Command, url: string) {
    if(command instanceof ListGamesCommand){    // For polling commands, use poll handler.
      this.http.post<ICommandArray>(
        url,
        new CommandArray([command]),
        { observe: 'response'}).subscribe(
          result => this.handleReponse(result),
          error => this.handleFailedPoll(error));
    } else {
      this.http.post<ICommandArray>(
        url,
        new CommandArray([command]),
        { observe: 'response'}).subscribe(
          result => this.handleReponse(result),
          error => this.handleError(error));
    }
  }

  /**
   * Polls the backend without transmitting and paramaters
   */
  public poll() {
    this.http.get<ICommandArray>(
      this.getUrl(),
      { observe: 'response' }).subscribe(
        result => this.handleReponse(result),
        error => this.handleFailedPoll(error));
  }

  constructor(private http: HttpClient,
              private errorService: ErrorNotifierService) {
    this.incomingCmd$.subscribe(cmd => {
      // Listen for authTokens
      const loginResult: any = cmd as any;

      if (loginResult.command !== 'loginResult' && loginResult.error === '') { return; }

      this.authToken = loginResult.user.authToken;
    });
  }
}
