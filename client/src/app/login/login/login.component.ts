import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { isDevMode } from '@angular/core';

@Component({
  selector: 'app-login-error-dialog',
  templateUrl: 'login-error-dialog.component.html',
})
export class LoginErrorDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<LoginErrorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public message: string) {}

  onOkClick(): void {
    this.dialogRef.close();
  }

}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private loginService: LoginService,
              public dialog: MatDialog) { }

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.maxLength(16)]),
    password: new FormControl('', [Validators.required]),
  });


  ngOnInit() {
    // Subscribe to the loginService to be informed of any errors that occur
    this.loginService.loginError$.subscribe(loginError => this.loginError(loginError));

    // Avoid having to type in a dummy username and password over and over
    if (isDevMode()) {
      this.loginForm.get('username').setValue('bob');
      this.loginForm.get('password').setValue('hunter2');
    }
  }

  /**
   * Opens the dialog and displays the given error
   * @param loginError The error to display
   */
  loginError(loginError: string) {
    const dialogRef = this.dialog.open(LoginErrorDialogComponent, {
      width: '400px',
      data: loginError
    });
  }

  /**
   * For a given control, identifies which error string to display.
   * @param control Which control to reference
   */
  getHumanReadableErrors(control: string) {
    let out = '';
    const errors = this.loginForm.get(control).errors;

    if (!errors) { return out; }

    if (errors.hasOwnProperty('maxlength')) { out += 'Maximum length is 16 characters. '; }
    if (errors.hasOwnProperty('required')) { out += 'You must enter a value. '; }

    return out;
  }

  onLogin() {
    this.loginService.commenceLogin(this.loginForm.get('username').value,
    this.loginForm.get('password').value);
  }

  onRegister() {
    this.loginService.commenceRegister(this.loginForm.get('username').value,
    this.loginForm.get('password').value);
  }

}
