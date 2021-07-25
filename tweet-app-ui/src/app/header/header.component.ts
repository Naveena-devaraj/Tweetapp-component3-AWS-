import { getLocaleCurrencySymbol } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  constructor(private userService: UserService) { }

  ngOnInit(): void {

  }

  isLogin() {
    return this.userService.getLoggedIn();

  }
  onLogOut() {
    this.userService.setLoggedIn(false);
    this.userService.setLoginID(null);
  }
}
