import { Injectable } from '@angular/core';
import { Command, CommandArray, ICommandArray} from './command';
import { isDevMode } from '@angular/core';
import { HttpClient, HttpResponse, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { ErrorNotifierService } from './error-notifier.service';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class ServerProxyService {

  private commandEndpoint = '/command';

  private incomingCmdSrc = new Subject<Command>();
  public incomingCmd$ = this.incomingCmdSrc.asObservable();

  private getServerUrl(): string {
    if (isDevMode()) { return 'http://127.0.0.1:4300'; } else { return 'api.marylou.ga'; }
  }

  private getUrl(): string {
    return this.getServerUrl() + this.commandEndpoint;
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      this.errorService.notifyServerCommError(error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);

      this.errorService.notifyHttpError(error.status, error.error.message);
    }
  }

  private handleReponse(result: HttpResponse<CommandArray>) {
    if (!result.ok) { console.error('Got a response that isn\'t OK but didn\'t go to handleError(?)'); }

    const commands: CommandArray = result.body as CommandArray;

    for (const command of commands.commands) {
      this.incomingCmdSrc.next(command);
    }
  }

  public transmitCommand(command: Command) {
    this.http.post<ICommandArray>(
      this.getUrl(),
      new CommandArray([command]),
      { observe: 'response'}).subscribe(
        result => this.handleReponse(result),
        error => this.handleError(error));

  }

  public poll() {

  }

  constructor(private http: HttpClient,
              private errorService: ErrorNotifierService) { }
}
