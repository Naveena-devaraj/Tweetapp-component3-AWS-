package com.tweetapp.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tweetapp.model.User;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
	User findByLoginIDAndPassword(String loginID, String password);

	User findByLoginID(String loginID);

	User findByEmail(String email);

	List<User> findAll();

	List<User> findByLoginIDContaining(String loginID);

}
