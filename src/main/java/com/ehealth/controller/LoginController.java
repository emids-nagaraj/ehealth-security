package com.ehealth.controller;

import static com.ehealth.security.SecurityConstants.EXPIRATION_TIME;
import static com.ehealth.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@RestController
@RequestMapping("/auth")
public class LoginController {
	  @Autowired
	    AuthenticationManager authenticationManager;
	/*
	 * @Autowired JwtTokenProvider jwtTokenProvider;
	 */
	  private CustomSuccessResponse customResponse;
	    @Autowired
	    IUserService userService;
		@PostMapping("/signin")
		public ResponseEntity<?> signin(@RequestBody com.ehealth.enity.User  data) {
	        try {
	            String email = data.getEmail();
	        	Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
	    		String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
	    				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	    				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
			
			  Map<Object, Object> model = new HashMap<>(); model.put("username", email);
			  model.put("token", token);
	            customResponse = new CustomSuccessResponse(null, null, model);

				return new ResponseEntity<>(customResponse, HttpStatus.OK);
	        } catch (AuthenticationException e) {
	            throw new BadCredentialsException("Invalid username/password supplied");
	        }
	    } 
	    
}
