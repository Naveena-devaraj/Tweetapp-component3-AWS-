import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Tweet } from '../model/tweet';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class TweetService {
  private baseUrl = environment.baseUrl;
  constructor(private httpClient: HttpClient, private userService: UserService) { }
  getAllTweets() {
    return this.httpClient.get<any>(this.baseUrl + "all")
  }
  getAllTweetsOfUser(userName: string) {
    return this.httpClient.get<any>(this.baseUrl + userName)
  }
  likeTweet(tweet: Tweet) {
    return this.httpClient.put<any>(this.baseUrl + "/" + this.userService.getLoginID() + "/like/" + tweet.id, tweet)
  }
  postComment(tweet: Tweet) {
    return this.httpClient.post<any>(this.baseUrl + "/" + this.userService.getLoginID() + "/reply/" + tweet.id, tweet)
  }
  deleteTweet(tweetId: string) {
    return this.httpClient.delete<any>(this.baseUrl + "/" + this.userService.getLoginID() + "/delete/" + tweetId)
  }
  updateTweet(tweet: Tweet) {
    return this.httpClient.put<any>(this.baseUrl + "/" + this.userService.getLoginID() + "/update/" + tweet.id, tweet)
  }
  postTweet(tweet: Tweet) {
    return this.httpClient.post<any>(this.baseUrl + "/" + this.userService.getLoginID() + "/add/", tweet)
  }
}
