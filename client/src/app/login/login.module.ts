import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent, LoginErrorDialog } from './login/login.component';
import { MatDialogModule, MatButtonModule, MatInputModule, MatFormFieldModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [LoginComponent, LoginErrorDialog],
  entryComponents: [ LoginErrorDialog ],
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule,
    MatButtonModule, MatInputModule, MatFormFieldModule, MatDialogModule
  ]
})
export class LoginModule { }
