package com.tweetapp.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.TweetappApplication;
import com.tweetapp.exception.InvalidInputDataException;
import com.tweetapp.model.ServiceResponse;
import com.tweetapp.model.User;
import com.tweetapp.service.UserService;

@RestController
@RequestMapping("/api/v1.0/tweets/")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetappApplication.class);
	@Autowired
	UserService userService;

	@PostMapping("register")
	public ServiceResponse registerUser(@RequestBody @Valid User user) throws Exception {
		LOGGER.info("Inside register user controller method");
		return userService.createUser(user);

	}

	@PutMapping("login")
	public User loginUser(@RequestBody User user) throws Exception {
		LOGGER.info("Inside login user controller method");
		if (StringUtils.isBlank(user.getLoginID()))
			throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "LoginId cannot be empty");
		else if (StringUtils.isBlank(user.getPassword()))
			throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Password cannot be empty");
		return userService.loginUser(user.getLoginID(), user.getPassword());

	}

	@PutMapping("forgot")
	public boolean updatPassword(@RequestBody User user) throws Exception {
		LOGGER.info("Inside update password controller method");
		if (StringUtils.isBlank(user.getLoginID()))
			throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "LoginId cannot be empty");
		if (StringUtils.isBlank(user.getPassword()))
			throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Password cannot be empty");
		return userService.forgotPassword(user.getLoginID(), user.getPassword());

	}

	@GetMapping("users/all")
	public List<User> getAllUsers() throws Exception {
		LOGGER.info("Inside getAllUsers controller method");

		return userService.getAllUsers();

	}

	@GetMapping("users/search/{loginID}")
	public Set<String> searchByUsername(@PathVariable String loginID) throws Exception {
		LOGGER.info("Inside searchByUsername controller method");
		if (StringUtils.isNotBlank(loginID))
			return userService.searchByUsername(loginID);
		else {
			throw new InvalidInputDataException("400", "loginID contains atlease 3 char");
		}

	}

}
