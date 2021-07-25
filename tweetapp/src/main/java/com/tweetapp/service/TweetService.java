package com.tweetapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetappApplication;
import com.tweetapp.exception.ApplicationServiceException;
import com.tweetapp.exception.InvalidInputDataException;
import com.tweetapp.exception.NoDataFoundException;
import com.tweetapp.model.Reply;
import com.tweetapp.model.ServiceResponse;
import com.tweetapp.model.Tweet;
import com.tweetapp.repository.TweetRepository;

@Service
public class TweetService {

	@Autowired
	TweetRepository tweetRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetappApplication.class);

	public ServiceResponse updateTweet(String loginID, @Valid Tweet tweet, String id) {
		try {
			LOGGER.info("Inside updateTweet  service method");

			Tweet existingTweet = tweetRepository.findById(id).get();
			if (existingTweet != null) {
				existingTweet.setTweetMessage(tweet.getTweetMessage());

				existingTweet.setModifiedDate(new Date());
				tweetRepository.save(existingTweet);
				ServiceResponse response = new ServiceResponse();
				response.setStatus("Success");
				response.setMsg("Tweet updated successfully");
				return response;

			} else
				throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Invalid id for existing tweet");

		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			throw new ApplicationServiceException("Unknown_500",
					"Oops Something unexpected happened.Please try again later ");
		}

	}

	public ServiceResponse deleteTweet(String loginID, String id) {
		try {
			LOGGER.info("Inside deleteTweet  service method");
			tweetRepository.deleteById(id);
			ServiceResponse response = new ServiceResponse();
			response.setStatus("Success");
			response.setMsg("Tweet deleted successfully");
			return response;
		} catch (Exception e) {
			LOGGER.info("Exception occurred in deleteTweet method in Tweet Service");
			throw e;
		}
	}

	public List<Tweet> getAllTweets() {
		try {

			LOGGER.info("Inside getAllTweets  service method");
			List<Tweet> allTweets = tweetRepository.findAll();
			if (allTweets.size() > 0) {
				return allTweets;
			} else {
				throw new NoDataFoundException("204", "No data available");

			}

		} catch (NoDataFoundException ex) {
			throw ex;
		}

		catch (Exception e) {
			LOGGER.info("Exception occurred in getAllTweets method in Tweet Service");
			throw e;
		}
	}

	public List<Tweet> getAllTweetsOfUser(String loginID) {

		try {
			LOGGER.info("Inside getAllTweetsOfUser service method");
			List<Tweet> allTweets = tweetRepository.findByLoginID(loginID);

			if (allTweets.size() > 0) {
				return allTweets;
			} else {
				throw new NoDataFoundException("204", "No Tweets to display");

			}

		} catch (NoDataFoundException ex) {
			throw ex;
		} catch (Exception e) {
			LOGGER.info("Exception occurred in getAllTweetsOfUser method in Tweet Service");
			throw e;
		}
	}

	public ServiceResponse replyTweet(String loginID, @Valid Tweet tweet, String id) {

		try {
			LOGGER.info("Inside replyTweet  service method");

			Tweet existingTweet = tweetRepository.findById(id).get();
			List<Reply> replies = new ArrayList<Reply>();
			if (existingTweet != null) {
				if (existingTweet.getReply() != null && !existingTweet.getReply().isEmpty()) {
					replies = existingTweet.getReply();

				}

				for (Reply reply : tweet.getReply()) {
					reply.setReplyCreatedDate(new Date());
					replies.add(reply);
				}

				existingTweet.setReply(replies);
				tweetRepository.save(existingTweet);
				ServiceResponse response = new ServiceResponse();
				response.setStatus("Success");
				response.setMsg("Reply added successfully");
				return response;
			} else
				throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Invalid id for existing tweet");

		} catch (InvalidInputDataException ex) {
			throw ex;
		} catch (Exception e) {
			LOGGER.info("Exception occurred in replyTweet method in Tweet Service");
			throw e;
		}
	}

	public ServiceResponse likeTweet(String loginID, @Valid Tweet tweet, String id) {

		try {
			LOGGER.info("Inside likeTweet  service method");

			Tweet existingTweet = tweetRepository.findById(id).get();
			if (existingTweet != null) {
				if (existingTweet.getLikes() != null && !existingTweet.getLikes().isEmpty()) {
					List<String> likes = existingTweet.getLikes();

					likes.addAll(tweet.getLikes());
					existingTweet.setLikes(likes);
					tweetRepository.save(existingTweet);

				} else {
					existingTweet.setLikes(tweet.getLikes());
					tweetRepository.save(existingTweet);

				}
				ServiceResponse response = new ServiceResponse();
				response.setStatus("Success");
				response.setMsg("Likes added successfully");
				return response;
			} else
				throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Invalid id for existing tweet");

		} catch (InvalidInputDataException ex) {
			throw ex;
		} catch (Exception e) {
			LOGGER.info("Exception occurred in replyTweet method in Tweet Service");
			throw e;
		}
	}

	public void postTweet(Tweet tweet) {

		try {
			LOGGER.info("Inside postTweet  service method");
			tweet.setCreatedDate(new Date());
			tweet.setModifiedDate(new Date());
			tweetRepository.save(tweet);

		} catch (Exception e) {
			LOGGER.info("Exception occurred in postTweet method in Tweet Service");
			throw e;
		}

	}

}
