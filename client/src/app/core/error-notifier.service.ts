import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export class ErrorMessage {
  constructor(
    public subheading: string,
    public errorline1: string,
    public errorline2: string,
    public recoverable: boolean ) {}

}

/**
 * This service is responsible for emitting errors when something bad happens
 *
 * The main app component listens and displays a modal when this service emits.
 */
@Injectable({
  providedIn: 'root'
})
export class ErrorNotifierService {

  constructor() { }

  private errorSource = new Subject<ErrorMessage>();
  public errors$ = this.errorSource.asObservable();

  public notify(message: string) {
    this.errorSource.next(new ErrorMessage('', message, '', true));
  }

  public notifyMessage(message: ErrorMessage) {
    this.errorSource.next(message);
  }

  public notifyHttpError(error: number, message: string) {
    this.notifyMessage( new ErrorMessage(
      'Failed to communicate with the server.',
      'HTTP status code ' + error,
      message, true));
  }

  /**
   * Used when the client can't communicate at all with the server (disconnect).
   * Must be closed by a successfull poll/transmission
   * @param message Message to display to user
   */
  public notifyServerCommError(message: string) {
    this.notifyMessage( new ErrorMessage(
      'Unable to connect to the server. Retrying...',
      message, '', false));
  }
}
