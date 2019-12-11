package com.ehealth.security;

import static com.ehealth.security.SecurityConstants.EXPIRATION_TIME;
import static com.ehealth.security.SecurityConstants.SECRET;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ehealth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Nagaraj
 *
 */ 
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private UserService userService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			com.ehealth.enity.User creds = new ObjectMapper().readValue(req.getInputStream(),
					com.ehealth.enity.User.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		// logger.info("ppppppppppppppps");
		String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String clientEmail = ((User) auth.getPrincipal()).getUsername();

		Optional<com.ehealth.enity.User> userOptional = userService.getUserByEmail(clientEmail);

		JSONObject loginResp = new JSONObject();
		loginResp.put("token", token);

		userOptional.ifPresent(user -> loginResp.put(SecurityConstants.ISADMIN, user.getIsAdmin()));

		res.getWriter().write(loginResp.toString());
	}
}
