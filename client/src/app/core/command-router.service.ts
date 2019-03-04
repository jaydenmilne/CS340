import { Injectable } from '@angular/core';
import { ServerProxyService } from '@core/server/server-proxy.service';
import { Command } from '@core/command';
import { Subject } from 'rxjs';
import { LoginResult } from './login-commands';
import { ErrorNotifierService, ErrorMessage } from './error-notifier.service';
import { GameCreatedCommand, StartGameCommand, RefreshGameListCommand } from './lobby-commands';
import { UpdatePlayerCommand, ChangeTurnCommand } from './game-commands.ts';

@Injectable({
  providedIn: 'root'
})
export class CommandRouterService {

  constructor(private serverProxy: ServerProxyService,
              private errorNotifier: ErrorNotifierService) {
    this.serverProxy.incomingCmd$.subscribe(command => this.handleIncomingCommand(command));
  }

  // ------------------------------
  // OBSERVABLES (one for every client command type)

  public loginResult$     = new Subject<LoginResult>();
  public gameCreated$     = new Subject<GameCreatedCommand>();
  public startGame$       = new Subject<StartGameCommand>();
  public refreshGameList$ = new Subject<RefreshGameListCommand>();
  public updatePlayer$    = new Subject<UpdatePlayerCommand>();
  public changeTurn$      = new Subject<ChangeTurnCommand>();

  /**
   * Identifies each command, deserializes it, and signals the observables.
   * Will use the errorNotifier if anything goes wrong.
   * @param cmd command to identify, deserialize, and dispatch
   */
  handleIncomingCommand(cmd: Command) {
    try {
      switch (cmd.command) {
        case 'loginResult':
          this.loginResult$.next(new LoginResult(cmd));
          break;
        case 'gameCreated':
          this.gameCreated$.next(new GameCreatedCommand(cmd));
          break;
        case 'startGame':
          this.startGame$.next(new StartGameCommand(cmd));
          break;
        case 'refreshGameList':
          this.refreshGameList$.next(new RefreshGameListCommand(cmd));
          break;
        case 'updatePlayer':
          this.updatePlayer$.next(new UpdatePlayerCommand(cmd));
          break;
        case 'changeTurn':
          this.changeTurn$.next(new ChangeTurnCommand(cmd));
          break;

        default:
          const msg = new ErrorMessage(
            'Got an unknown command type from the server',
            'type: ' + cmd.command,
            'Command info: ' + JSON.stringify(cmd), true);

          this.errorNotifier.notifyMessage(msg);
          break;
        }
    } catch (e) {
      if (!(e instanceof TypeError)) {throw e; }
      const msg = new ErrorMessage(
        'Failed to deserialize something from the server',
        'type: ' + cmd.command,
        'Exception Details: ' + e.message, true);

        this.errorNotifier.notifyMessage(msg);
    }
  }
}
