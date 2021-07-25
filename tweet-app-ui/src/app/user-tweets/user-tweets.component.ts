import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Event } from '@angular/router';
import { Tweet } from '../model/tweet';
import { TweetService } from '../service/tweet.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-tweets',
  templateUrl: './user-tweets.component.html',
  styleUrls: ['./user-tweets.component.css']
})
export class UserTweetsComponent implements OnInit {
  error: string;
  tweetList: Tweet[];
  searchChar = new FormControl();
  searchList: string[];
  tweetMsg = new FormControl();
  constructor(private tweetService: TweetService, private userService: UserService) { }

  ngOnInit(): void {
    this.getAllTweets();
  }
  onChange(): void {
    if (this.searchChar.value == null || this.searchChar.value == "") {
      this.getAllTweets();
    }
    this.userService.searchWithUserName(this.searchChar.value).subscribe(data => {
      this.searchList = data;
      console.log(this.searchList);
    },
      (responseError) => {

        if (responseError.status == 204) {
          this.error = responseError.error.message;
          ;
        }
      });



  }
  onSearch(event: any): void {
    this.tweetService.getAllTweetsOfUser(event).subscribe(data => {
      this.tweetList = data;
      console.log(this.tweetList);
    },
      (responseError) => {
        console.log(responseError.error.message);
      });
  }

  postTweet() {
    let newTweet: Tweet = {
      id: null,
      reply: [],
      loginID: null,
      tweetMessage: null,

      likes: null,
      modifiedDate: null,

    }
    newTweet.tweetMessage = this.tweetMsg.value;
    newTweet.loginID = this.userService.getLoginID();

    this.tweetService.postTweet(newTweet).subscribe(
      (response) => {
        this.getAllTweets();
      },
      (responseError) => {
        if (responseError)
          console.log(responseError.error.message);
      });
    this.tweetMsg.setValue("");
  }


  getAllTweets() {
    this.tweetService.getAllTweets().subscribe(data => {
      this.tweetList = data;
    },
      (responseError) => {

        if (responseError.status == 204) {
          console.log(responseError.error.message);
        }
      });
  }

}
