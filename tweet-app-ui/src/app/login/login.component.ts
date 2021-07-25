import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  isLoggedIn: boolean;
  error: string;


  constructor(private fb: FormBuilder, private router: Router, private userService: UserService) { }

  ngOnInit(): void {

    // this.isLoggedIn = this.authService.loggedIn;
    this.loginForm = this.fb.group({
      loginID: ['', [
        Validators.required
      ]],
      password: ['', [
        Validators.required
      ]]
    })
  }
  get loginID() { return this.loginForm.get('loginID'); }
  get password() { return this.loginForm.get('password'); }

  login() {
      this.error='';
    this.userService.login(this.loginForm.value).subscribe(data => {
      console.log('Login Successful!');
      this.userService.setLoggedIn(true);
      this.userService.setLoginID(this.loginForm.value.loginID);
      this.router.navigate(['/allTweets']);
      // this.router.navigate(['/profileTweet'],{ queryParams: { userInfo: data.firstName,
      //   lastName: data.lastName,contactNum: data.contactNumber,username:data.loginId,email:data.email }});
    },
      (responseError) => {

        if (responseError.status == 400) {
          this.error = "Invalid Username/password";
        }
      });

  }
}


