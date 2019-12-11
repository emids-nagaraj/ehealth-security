package com.ehealth.controller;
 
import java.util.Map;

import javax.ws.rs.WebApplicationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.ehealth.enity.User;
import com.ehealth.service.UserService;
import com.ehealth.util.CustomSuccessResponse;
import com.ehealth.util.EHealthConstants; 
/**
 * @author nagaraj
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final ModelMapper modelMapper = new ModelMapper();
	private Map<Object, Object> returnMap;

	@Autowired
	private UserService userService;
 
	private CustomSuccessResponse customResponse;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") String id) {
		logger.info("Fetching User with email {}", id);

		try {
			User user = userService.getUserByEmail(id).get();

			user.setPassword(null);
			customResponse = new CustomSuccessResponse(null, null, user);

			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}

		catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(null, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//@RequestMapping("/register", method = RequestMethod.POST)
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User userDTO) {

		logger.info("Creating user with emailID : {}", userDTO.getEmail());
		try {
			User newUser = userService.save(userDTO);
			String successMessage = EHealthConstants.USER_PREFIX + " " + EHealthConstants.CREATION_SUCCESS;

			newUser.setPassword(null);
			customResponse = new CustomSuccessResponse(true, successMessage, newUser);

			logger.info(successMessage);
			return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
		}

		catch (WebApplicationException e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}

		catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		logger.info("Updating User with email {}", user.getEmail());

		try {
			User newUser = userService.updateUser(user);
			String successMessage = EHealthConstants.USER_PREFIX + " " + EHealthConstants.UPDATE_SUCCESS;

			newUser.setPassword(null);
			customResponse = new CustomSuccessResponse(true, successMessage, newUser);

			logger.info(successMessage);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}

		catch (WebApplicationException e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
		logger.info("Fetching & Deleting User with email {}", email);

		try {
//			userService.deleteUser(email);
			String successMessage = EHealthConstants.USER_PREFIX + " " + EHealthConstants.DELETE_SUCCESS;

			customResponse = new CustomSuccessResponse(true, successMessage, null);

			logger.info(successMessage);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		}

		catch (WebApplicationException e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
		}

		catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			customResponse = new CustomSuccessResponse(false, e.getLocalizedMessage(), null);
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping("/emails")
	public ResponseEntity<?> getEmails() {

		return new ResponseEntity<>(userService.getUsersEmailList(), HttpStatus.OK);

	}

	@GetMapping("/isAdmin/{email}")
	public ResponseEntity<?> isAdmin(@PathVariable("email") String email) {
		User user = userService.getUserByEmail(email).get();
		return new ResponseEntity<>(user.getIsAdmin(), HttpStatus.OK);

	}

}
