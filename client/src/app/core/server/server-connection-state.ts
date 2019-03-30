import { ServerConnectionService } from './server-connection.service';
import { CommandArray, ICommandArray } from '@core/command';
import { HttpHeaders } from '@angular/common/http';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ListGamesCommand } from '@core/lobby-commands';

export class ServerConnectionState {
  private COMMAND_ENDPOINT = '/command';

  constructor(protected connection: ServerConnectionService) { }

  public handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent || error.status === 0) {
      // A client-side or network error occurred.
      // Go to the reconnecting state
      this.connection.changeState(new ReconnectingState(this.connection));
      this.connection.disconnectedFromServer$.next();
      // Send a notification (for the error message)
      this.connection.errorService.notifyServerCommError(error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,


        let message = error.message;
        
        if (error.error != null) {
          console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);

          message += '. ' + error.error
        }

      this.connection.errorService.notifyHttpError(error.status, message);
    }
  }

  /**
   * Returns the endpoint for this state
   */
  public getEndpoint(): string {
    return this.COMMAND_ENDPOINT;
  }

  public handleResponse(result: HttpResponse<CommandArray>) {
    const commands: CommandArray = result.body as CommandArray;

    // Dispatch every command in the received array
    for (const command of commands.commands) {
      this.connection.incomingCmd$.next(command);
    }
  }

  /**
   * Sends a GET to the active endpoint.
   */
  public poll() {
    this.connection.http.get<ICommandArray>(
    this.connection.getServerUrl() + this.getEndpoint(),
    {
      headers: this.getHeaders(),
      observe: 'response'
    }).subscribe(
      result => this.connection.handleResponse(result),
      error => this.connection.handleError(error));
  }

  public getHeaders(): HttpHeaders {
    return new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': this.connection.authToken
    });
  }
}

/**
 * Used when an HTTP transport-level error happens (not a status code error)
 * Upon a successfull transmission, goes back to the previous state (before the disconnect)
 * Ignores any transmission errors - since those are expected.
 */
export class ReconnectingState extends ServerConnectionState {

  public poll() {
    // If there is a command, poll with that instead of a GET
    if (this.connection.pendingCommand != null) {

      if (this.connection.pendingRequest) {
        alert('That impossible thing is happening. Shame Jayden.');
      }

      this.connection.transmitCommand(this.connection.pendingCommand);
    } else {
      super.poll();
    }
  }

  public handleResponse(result: HttpResponse<CommandArray>) {
    super.handleResponse(result);
    this.connection.goToPreviousState();
  }

  public handleError(error: HttpErrorResponse) {
    // nop - we are kind of expecting errors in this state.
    // Wait for reconnection
  }

}

/**
 * Initial state, used for login. Uses the /register endpoint and skips auth headers.
 * No polling either.
 */
export class LoginState extends ServerConnectionState {
  private REGISTER_ENDPOINT = '/register';

  public poll() {
    // nop - no polling in login
  }

  public getHeaders() {
    // Don't send auth header in login
    return new HttpHeaders({
        'Content-Type':  'application/json'
    });
  }

  public getEndpoint(): string {
    return this.REGISTER_ENDPOINT;
  }

}

/**
 * State used in the lobby screen. Polls with the ListGamesCommand instead of just a GET
 */
export class LobbyState extends ServerConnectionState {
  public poll() {
    // Poll with the ListGamesCommand in the lobby
    this.connection.transmitCommand(new ListGamesCommand);
  }
}

/**
 * State for the game screen.
 */
export class GameState extends ServerConnectionState {
  // So far, no changes to base class needed.
}
