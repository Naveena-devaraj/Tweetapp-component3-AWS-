import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Reply } from '../model/reply';
import { Tweet } from '../model/tweet';
import { TweetService } from '../service/tweet.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent implements OnInit {
  @Input() tweetList: Tweet[];

  comment = new FormControl();
  tweetMessage = new FormControl();
  display: boolean = false;
  tweetId: string = "";
  loginID: string;
  isCommentShow: boolean = false;
  commentID: string = "";
  


  constructor(private tweetService: TweetService, private userService: UserService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.loginID = this.userService.getLoginID();
    
  }


  likeTweet(tweet: Tweet) {

    let likes: string[] = [];

    likes.push(this.userService.getLoginID());
    tweet.likes = likes;

    this.tweetService.likeTweet(tweet).subscribe(
      (response) => {
        this.getAllTweets();
      },
      (responseError) => {
        if (responseError)
          console.log(responseError.error.message);
      });

  }


  postComment(tweet: Tweet, comment: string) {
    let newTweet: Tweet = {
      id: null,
      reply: [],
      loginID: null,
      tweetMessage: null,

      likes: null,
      modifiedDate: null,

    }

    newTweet.id = tweet.id;
    newTweet.tweetMessage = tweet.tweetMessage;
    newTweet.loginID = tweet.loginID;

    let reply: Reply = {
      loginID: null,
      replyMsg: null,
      replyCreatedDate: null
    }
    reply.replyMsg = comment;
    reply.loginID = this.loginID;
    let replyList: Reply[] = [];
    replyList.push(reply);
    newTweet.reply = replyList;


    this.tweetService.postComment(newTweet).subscribe(
      (response) => {
        this.getAllTweets();
      },
      (responseError) => {
        if (responseError)
          console.log(responseError.error.message);
      });
    this.comment.setValue("");
  }


  deleteTweet(tweeId: string) {
   
    this.tweetService.deleteTweet(tweeId).subscribe(
      (response) => {
        this.getAllTweets();
      },
      (responseError) => {
        if (responseError)
          console.log(responseError.error.message);
      });

  }



  onEdit(tweet: Tweet) {
    this.display = true;
    this.tweetId = tweet.id;
    this.tweetMessage.setValue(tweet.tweetMessage);

  }

  viewComments(tweet: Tweet) {
    this.commentID = tweet.id;
    this.isCommentShow = true;
  }

  closeCommentBox() {
    this.commentID = "";
    this.isCommentShow = false;
  }

  updateTweet(tweet: Tweet, tweetMessage: string) {
    tweet.tweetMessage = tweetMessage;
    this.tweetService.updateTweet(tweet).subscribe(
      (response) => {
        this.getAllTweets();
      },
      (responseError) => {
        if (responseError)
          console.log(responseError.error.message);
      });
    this.display = false;
    this.tweetId = "";

  }

  cancelUpdateTweet() {
    this.display = false;
    this.tweetId = "";

  }

  getAllTweets() {
    this.tweetService.getAllTweets().subscribe(data => {
      this.tweetList = data;
    })
  }
}
