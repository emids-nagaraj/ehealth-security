package com.ehealth.controller;

import static com.ehealth.security.SecurityConstants.EXPIRATION_TIME;
import static com.ehealth.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehealth.service.IUserService;
import com.ehealth.util.CustomSuccessResponse;
import com.ehealth.util.EHealthConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/auth")
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	AuthenticationManager authenticationManager;

	private CustomSuccessResponse customResponse;
	@Autowired
	IUserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody com.ehealth.enity.User data) {
		try {
			String email = data.getEmail();
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
			String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
					.signWith(SignatureAlgorithm.HS512, SECRET).compact();

			Map<Object, Object> model = new HashMap<>();
			model.put("username", email);
			model.put("token", token);
			String successMessage = EHealthConstants.USER_PREFIX + " " + EHealthConstants.AUTHENTICATED_SUCCESSFULLY;
			customResponse = new CustomSuccessResponse(true, successMessage, model);

			logger.info(successMessage);
			return new ResponseEntity<>(customResponse, HttpStatus.OK);
		} catch (AuthenticationException e) {
//			throw new BadCredentialsException("Invalid username/password supplied");
			logger.error(e.toString());
			customResponse = new CustomSuccessResponse(false, "Invalid username or password supplied", null);
			return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
		}
	}

}
