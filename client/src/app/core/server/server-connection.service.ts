import { Injectable } from '@angular/core';
import { Command, CommandArray, ICommandArray } from '@core/command';
import { isDevMode } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ErrorNotifierService } from '@core/error-notifier.service';
import { Subject, BehaviorSubject } from 'rxjs';
import { ServerConnectionState } from './server-connection-state';

@Injectable({
  providedIn: 'root'
})
export class ServerConnectionService {
  private previousState: ServerConnectionState;
  private state : ServerConnectionState;
  private serverUrlOverride: string = "";
  
  // Queue of commands that are ready to be sent
  private outgoingQueue = new Array<Command>();
  // Command that has a pending request, or couldn't be send because of a transmission error.
  public pendingCommand: Command;
  // If there is a poll or command being sent. If this is true, commands will be queued.
  public pendingRequest = false;

  public authToken = '';

  public incomingCmd$ = new Subject<Command>();
  public disconnectedFromServer$ = new Subject<null>();

  // Signalled when the client is able to talk with the server (even if it is a status code error)
  // Used to alert the rest of the app that the client reconnected.
  public transmissionOk$ = new Subject<null>();

  constructor(public http: HttpClient,
    public errorService: ErrorNotifierService) {
  }

  public changeState(newState: ServerConnectionState) {
    this.previousState = this.state;
    this.state = newState;
  }

  public goToPreviousState() {
    this.state = this.previousState;
  }

  public registerAuthToken(token : string) {
    this.authToken = token;
  }

  public setServerUrl(url: string) {
    this.serverUrlOverride = url;
  }

  /**
   * Gets the correct url depending on development mode or not
   */
  public getServerUrl(): string {
    if (this.serverUrlOverride != "") {
      return this.serverUrlOverride;
    }
    if (isDevMode()) { return 'http://127.0.0.1:4300'; } else { return 'api.marylou.ga'; }
  }

  /**
   * When an HTTP error occurs, handle it and notify the user
   * @param error the error to parse
   */
  public handleError(error: HttpErrorResponse) {
    this.pendingRequest = false;

    this.state.handleError(error);
  }

  /**
   * Parses the commands out of a response from the server
   * @param result response to parse
   */
  public handleResponse(result: HttpResponse<CommandArray>) {
    this.pendingRequest = false;
    this.pendingCommand = null;
    this.transmissionOk$.next();

    this.state.handleResponse(result);

    // If there are any pending commands, send them
    if (this.outgoingQueue.length != 0) {
      this.transmitCommand(this.outgoingQueue.pop());
    }
  }

  /**
   * Sends a command to the server if there are no pending requests, otherwise it
   * enqueues it.
   * @param command Command-like object to transmit to the server
   */
  public transmitCommand(command: Command) {
    if (this.pendingRequest) {
      this.outgoingQueue.unshift(command);
    } else {
      this.sendCommandToEndpoint(command, this.getServerUrl() + this.state.getEndpoint());
    }
  }

  /**
   * Sends a command to given endpoint
   * @param command command like object
   * @param url where to send it
   */
  private sendCommandToEndpoint(command: Command, url: string) {
    this.pendingCommand = command;
    this.pendingRequest = true;

    this.http.post<ICommandArray>(
      url,
      command,
      {
        headers: this.state.getHeaders(),
        observe: 'response'
      }).subscribe(
        result => this.handleResponse(result),
        error => this.handleError(error));
  }

  /**
   * Polls the backend in the state-appropriate manner. If there is a pending request,
   * nothing happens, since we'll hear back from the server soon enough.
   */
  public poll() {
    if (!this.pendingRequest) {
      this.state.poll();
    }
  }
}
