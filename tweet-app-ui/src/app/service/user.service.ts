import { Injectable } from '@angular/core';
import { Observable, Observer } from 'rxjs';

import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/user';
import { ServiceResponse } from '../model/serviceresponse';




@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = environment.baseUrl;
  private loggedIn: boolean;
  private loginID: string;

  constructor(private httpClient: HttpClient) { }
  registerUser(user: User): Observable<any> {
    console.log("registerUser -> " + user);
    return this.httpClient.post<ServiceResponse>(this.baseUrl + "register", user)

  }
  login(user: User): Observable<any> {
    console.log("loginUser-> " + user);
    return this.httpClient.put<any>(this.baseUrl + "login", user)

  }
  updatePassword(user: User): Observable<any> {
    console.log("updatePassword ");
    console.log(user.loginID)
    return this.httpClient.put<boolean>(this.baseUrl + "forgot", user)

  }
  searchWithUserName(searchChar: string) {
    console.log("searchWithUserName-> " + searchChar);
    return this.httpClient.get<string[]>(this.baseUrl + "users/search/" + searchChar)
  }
  public setLoggedIn(loginStatus: boolean) {
    this.loggedIn = loginStatus;
  }
  public getLoggedIn() {
    return this.loggedIn;
  }
  public setLoginID(loginID: string) {
    this.loginID = loginID;
  }
  public getLoginID() {
    return this.loginID;
  }


}
