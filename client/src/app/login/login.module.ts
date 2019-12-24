import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent, LoginErrorDialogComponent } from './login/login.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [LoginComponent, LoginErrorDialogComponent],
  entryComponents: [ LoginErrorDialogComponent ],
  imports: [
    CommonModule, FormsModule, ReactiveFormsModule,
    MatButtonModule, MatInputModule, MatFormFieldModule, MatDialogModule
  ]
})
export class LoginModule { }
