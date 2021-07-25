import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordMatchValidator } from '../model/passwordMatchValidator';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-updatepassword',
  templateUrl: './updatepassword.component.html',
  styleUrls: ['./updatepassword.component.css']
})
export class UpdatepasswordComponent implements OnInit {

  updateForm: FormGroup;
  passwordConfirmPasswordMismatch: boolean;
  error: string;


  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  ngOnInit(): void {

    this.updateForm = this.fb.group({
      loginID: ['', [
        Validators.required
      ]],
      password: ['', [
        Validators.required, Validators.minLength(6)
      ]],
      confirmPassword: ['', [
        Validators.required
      ]],
    }, {
      validator: PasswordMatchValidator('password', 'confirmPassword')
    })
  }
  get loginID() { return this.updateForm.get('loginID'); }
  get password() { return this.updateForm.get('password'); }
  get confirmPassword() { return this.updateForm.get('confirmPassword'); }

  updatePassword() {
    this.userService.updatePassword(this.updateForm.value).subscribe(data => {
      console.log('password updated Successfully!');
      this.router.navigate(["login"]);
    },
      (responseError) => {
        if (responseError.status == 400) {
          console.log(responseError.error.message)
          this.error = responseError.error.message;
        }
      });

  }
}
