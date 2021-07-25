package com.tweetapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetappApplication;
import com.tweetapp.exception.InvalidInputDataException;
//import com.tweetapp.kafka.KafkaPublisher;
import com.tweetapp.model.ServiceResponse;
import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetService;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class TweetController {
	@Autowired
	TweetService tweetService;
//	@Autowired
//	KafkaPublisher kafkaPublisher;
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetappApplication.class);

	@PostMapping("{loginID}/add")
	public void postTweet(@PathVariable String loginID, @RequestBody @Valid Tweet tweet) throws Exception {
		LOGGER.info("Inside postTweet  controller method");

//		kafkaPublisher.sendMessageToTopic(tweet.getTweetMessage());
		tweetService.postTweet(tweet);

	}

	@PutMapping("{loginID}/update/{id}")
	public ServiceResponse updateTweet(@PathVariable String loginID, @RequestBody @Valid Tweet tweet,
			@PathVariable String id) throws Exception {
		LOGGER.info("Inside updateTweet  controller method");
		String tweetID = id.trim();
		return tweetService.updateTweet(loginID, tweet, tweetID);

	}

	@DeleteMapping("{loginID}/delete/{id}")
	public ServiceResponse deleteTweet(@PathVariable String loginID, @PathVariable String id) throws Exception {
		LOGGER.info("Inside deleteTweet  controller method");
		String tweetID = id.trim();
		return tweetService.deleteTweet(loginID, tweetID);

	}

	@GetMapping("all")

	public List<Tweet> getAllTweets() throws Exception {
		LOGGER.info("Inside getAllTweets controller method");

		return tweetService.getAllTweets();

	}

	@GetMapping("{loginID}")

	public List<Tweet> getAllTweetsOfUser(@PathVariable String loginID) throws Exception {
		LOGGER.info("Inside getAllTweetsOfUser controller method");

		return tweetService.getAllTweetsOfUser(loginID);

	}

	@PostMapping("{loginID}/reply/{id}")
	public ServiceResponse replyTweet(@PathVariable String loginID, @RequestBody @Valid Tweet tweet,
			@PathVariable String id) throws Exception {
		LOGGER.info("Inside replyTweet  controller method");
		String tweetID = id.trim();
		if (!tweet.getReply().isEmpty()) {
			return tweetService.replyTweet(loginID, tweet, tweetID);

		} else
			throw new InvalidInputDataException("400", "Reply cannot be empty");

	}

	@PutMapping("{loginID}/like/{id}")
	public ServiceResponse likeTweet(@PathVariable String loginID, @RequestBody @Valid Tweet tweet,
			@PathVariable String id) throws Exception {
		LOGGER.info("Inside likeTweet  controller method");
		String tweetID = id.trim();
		if (!tweet.getLikes().isEmpty()) {
			return tweetService.likeTweet(loginID, tweet, tweetID);

		} else
			throw new InvalidInputDataException("400", "likes cannot be empty");

	}

}
