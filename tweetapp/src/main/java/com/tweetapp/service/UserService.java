package com.tweetapp.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.TweetappApplication;
import com.tweetapp.exception.ApplicationServiceException;
import com.tweetapp.exception.InvalidInputDataException;
import com.tweetapp.exception.NoDataFoundException;
import com.tweetapp.exception.UserAlreadyExistsException;
import com.tweetapp.model.ServiceResponse;
import com.tweetapp.model.User;
import com.tweetapp.repository.UserRepository;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TweetappApplication.class);

	@Autowired
	UserRepository userRepository;

	public ServiceResponse createUser(User user) throws Exception {

		try {

			LOGGER.info("Inside create user method in user service");
			ServiceResponse response = new ServiceResponse();
			User existingEmailUsers = userRepository.findByEmail(user.getEmail());
			if (existingEmailUsers != null) {
				throw new UserAlreadyExistsException("Email already exists...");
			}

			userRepository.save(user);

			response.setStatus("Success");
			response.setMsg("User created successfully");

			return response;
		} catch (UserAlreadyExistsException ex) {
			throw ex;
		}

		catch (Exception ex) {
			throw new ApplicationServiceException("Unknown_500",
					"Internal server error occured while creating user" + ex.getMessage());
		}

	}

	public User loginUser(String loginID, String password) throws Exception {

		try {
			LOGGER.info("Inside loginUser method in user service");

			User user = userRepository.findByLoginIDAndPassword(loginID, password);
			if (user != null) {
				return user;
			} else {
				throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "Invalid LoginId/Password");

			}

		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			throw new ApplicationServiceException("Unknown_500",
					"Oops Something unexpected happened.Please try again later ");
		}

	}

	public boolean forgotPassword(String loginID, String password) {

		try {
			LOGGER.info("Inside forgotPassword method in user service");

			User savedUser = userRepository.findByLoginID(loginID);
			if (savedUser != null) {
				savedUser.setPassword(password);
				savedUser.setConfirmPassword(password);
				userRepository.save(savedUser);
				return true;
			} else
				throw new InvalidInputDataException("INPUT_DATA_INVALID_400", "User not exists");

		} catch (InvalidInputDataException ex) {
			throw ex;
		}

		catch (Exception ex) {
			throw new ApplicationServiceException("Unknown_500",
					"Oops Something unexpected happened.Please try again later ");
		}

	}

	public List<User> getAllUsers() {

		try {
			LOGGER.info("Inside getAllUsers method in user service");

			List<User> users = userRepository.findAll();
			if (users.size() > 0) {
				return users;
			} else {
				throw new NoDataFoundException("204", "No data available");

			}

		} catch (NoDataFoundException ex) {
			throw ex;
		}

		catch (Exception ex) {
			throw new ApplicationServiceException("Unknown_500",
					"Oops Something unexpected happened.Please try again later ");
		}
	}

	public Set<String> searchByUsername(String searchString) {
		LOGGER.info("Inside searchByUsername method in user service");
		try {

			List<User> users = userRepository.findByLoginIDContaining(searchString);
			if (users.size() > 0) {
				return users.stream().map(User::getLoginID).collect(Collectors.toSet());

			} else {
				throw new NoDataFoundException("204", "No data available");

			}

		} catch (NoDataFoundException ex) {
			throw ex;
		}

		catch (Exception e) {
			LOGGER.info("Exception occurred in searchByUsername method in User Service");
			throw e;
		}
	}

}
