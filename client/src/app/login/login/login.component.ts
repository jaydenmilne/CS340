import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

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

  usernameError = '';
  passwordError = '';


  ngOnInit() {
    this.loginService.loginError$.subscribe(loginError => this.loginError(loginError));
  }

  loginError(loginError: string) {
    const dialogRef = this.dialog.open(LoginErrorDialog, {
      width: '250px',
      data: loginError
    });
  }

  getHumanReadableErrors(control: string) {
    let out = '';
    const errors = this.loginForm.get(control).errors;

    if (!errors) { return out; }

    if (errors.hasOwnProperty('maxlength')) { out += 'Maximum length is 16 characters. '; }
    if (errors.hasOwnProperty('required')) { out += 'You must enter a value. '; }

    return out;
  }

  onLogin() {
    console.log('login pressed...');
    this.loginService.commenceLogin('a', 'b');
  }

  onRegister() {
    console.log('register pressed...');
  }

}

@Component({
  selector: 'login-error-dialog',
  templateUrl: 'login-error-dialog.component.html',
})
export class LoginErrorDialog {

  constructor(
    public dialogRef: MatDialogRef<LoginErrorDialog>,
    @Inject(MAT_DIALOG_DATA) public message: string) {}

  onOkClick(): void {
    this.dialogRef.close();
  }

}
