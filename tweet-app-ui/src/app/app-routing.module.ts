import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { TweetComponent } from './tweet/tweet.component';
import { UpdatepasswordComponent } from './updatepassword/updatepassword.component';
import { AuthguardGuard } from './service/authguard.guard';
import { UserTweetsComponent } from './user-tweets/user-tweets.component';

const routes: Routes = [
  {
    path: 'signup',
    component: SignupComponent
  }, {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'allTweets', component: UserTweetsComponent
    , canActivate: [AuthguardGuard]
  },

  {
    path: 'updatePassword',
    component: UpdatepasswordComponent
  }
, {
    path: '',
    component: LoginComponent
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
