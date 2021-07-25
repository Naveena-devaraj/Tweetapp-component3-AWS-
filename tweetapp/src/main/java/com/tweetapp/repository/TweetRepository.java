package com.tweetapp.repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tweetapp.model.Tweet;

@EnableScan
public interface TweetRepository extends CrudRepository<Tweet, String> {
	Optional<Tweet> findById(String id);

	List<Tweet> findAll();

	List<Tweet> findByLoginID(String loginID);


}
