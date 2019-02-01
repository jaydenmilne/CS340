import { Component, Inject } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { ErrorNotifierService, ErrorMessage } from '@core/error-notifier.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Mary Lou';
  constructor (public dialog: MatDialog,
               public errorService: ErrorNotifierService) {}

  ngOnInit() {
  this.errorService.errors$.subscribe(loginError => this.oops(loginError));
  }

  private oops(error: ErrorMessage) {
    const dialogRef = this.dialog.open(FatalErrorDialog, {
      width: '500px',
      data: error
    });

    dialogRef.afterClosed().subscribe(result => {
      // bye bye
    });
  }
}


@Component({
  selector: 'fatal-error-dialog',
  template: `
  <h1 mat-dialog-title>Thanos snapped and your game got dusted</h1>
  <img style="border-radius:8px; max-width:100%" src="/assets/img/thanos_snapping.png">
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
export class FatalErrorDialog {

  constructor(
    public dialogRef: MatDialogRef<FatalErrorDialog>,
    @Inject(MAT_DIALOG_DATA) public data: ErrorMessage) {}

  onReloadClick(): void {
    // bye bye
    window.location.reload();
  }

  onStruggleOn(): void {
    this.dialogRef.close();
  }

}
