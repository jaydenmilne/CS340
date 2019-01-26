import { Component, OnInit } from '@angular/core';
import {FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);

  getUsernameError() {
    return this.username.hasError('required') ? 'You must enter a value' :
            '';
  }

  getPasswordError() {
    return this.password.hasError('required') ? 'You must enter a value' :
            '';
  }

  constructor() { }

  ngOnInit() {
  }

}
