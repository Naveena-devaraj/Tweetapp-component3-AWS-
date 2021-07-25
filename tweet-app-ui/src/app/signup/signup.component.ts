import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordMatchValidator } from '../model/passwordMatchValidator';
import { UserService } from '../service/user.service';



@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;
  error: string = '';
  passwordConfirmPasswordMismatch: boolean;
  signupformValidation: boolean;

  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { };
  ngOnInit() {

    this.signupForm = this.fb.group({
      loginID: ['', [
        Validators.required, Validators.minLength(3)
      ]],
      email: ['', [
        Validators.required
        , Validators.email
      ]],
      firstName: ['', [
        Validators.required, Validators.minLength(3)
      ]],
      lastName: ['', [
        Validators.required, Validators.minLength(1)
      ]],
      password: ['', [
        Validators.required, Validators.minLength(6)
      ]],
      confirmPassword: ['', [
        Validators.required
      ]],
      contactNumber: ['', [
        Validators.required
        , Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")
      ]]
    }, {

      validator: PasswordMatchValidator('password', 'confirmPassword')
    });
  }
  get loginID() { return this.signupForm.get('loginID'); }
  get email() { return this.signupForm.get('email'); }
  get firstName() { return this.signupForm.get('firstName'); }
  get lastName() { return this.signupForm.get('lastName'); }
  get password() { return this.signupForm.get('password'); }
  get confirmPassword() { return this.signupForm.get('confirmPassword'); }
  get contactNumber() { return this.signupForm.get('contactNumber'); }


  onSubmit() {
    this.error='';
    console.log("Inside onsubmit method")
    console.log(this.signupForm.value);
    this.userService.registerUser(this.signupForm.value).subscribe(
      (response) => {
        this.router.navigate(['login']);
      },
      (responseError) => {
        if (responseError)
          this.error = responseError.error.message;
      });
  }

}