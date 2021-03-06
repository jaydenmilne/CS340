import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ErrorNotifierService, ErrorMessage } from '@core/error-notifier.service';
import { ServerConnectionService } from '@core/server/server-connection.service';
import { ServerPollerService } from '@core/server/server-poller.service';
import { HostListener } from '@angular/core/';

@Component({
  selector: 'app-fatal-error-dialog',
  template: `
  <h1 mat-dialog-title>Thanos snapped and your game got dusted</h1>
  <img style="border-radius:8px; height:217px; max-width:100%" src="/assets/img/thanos_snapping.png">
  <div mat-dialog-content>
    <h3>{{data.subheading}}</h3>
    <p>{{data.errorline1}}</p>
    <p>{{data.errorline2}}</p>
  </div>
  <div mat-dialog-actions>
    <button mat-button *ngIf="data.recoverable" (click)="onStruggleOn()">Struggle On</button>
    <button mat-button cdkFocusInitial (click)="onReloadClick()">Reload</button>
  </div>
  `
})
export class FatalErrorDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<FatalErrorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ErrorMessage,
    private serverConnection: ServerConnectionService) {
      if (!data.recoverable) {
        // Only way to recover is for there to be a successfull communication with the
        // server.
        serverConnection.transmissionOk$.subscribe(_ => this.onStruggleOn());
      }
    }

  onReloadClick(): void {
    // bye bye
    window.location.reload();
  }

  onStruggleOn(): void {
    this.dialogRef.close();
  }

}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Ticket to Ride: End Game';
  constructor (public dialog: MatDialog,
               public errorService: ErrorNotifierService,
               public pollerService: ServerPollerService,
               private serverConnection: ServerConnectionService) {}

  ngOnInit() {
  this.errorService.errors$.subscribe(loginError => this.oops(loginError));
  }

  @HostListener('document:keypress', ['$event'])
  private handleKeypress(event: any) {
    if (event.target.nodeName !== 'INPUT' && event.target.nodeName !== 'TEXTAREA') {
      switch (event.key) {
        case 'm':
        case 'p':
        this.pollerService.handleKeypress(event);
        break;
        case 'u':
        let url = prompt('Server URL:');
        if (!url) {
          return;
        }

        if (!url.startsWith('http')) {
          url = 'http://' + url;
        }
        this.serverConnection.setServerUrl(url);
        break;
      }
    }
  }

  private oops(error: ErrorMessage) {
    const dialogRef = this.dialog.open(FatalErrorDialogComponent, {
      width: '500px',
      data: error,
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      // bye bye
    });
  }
}
