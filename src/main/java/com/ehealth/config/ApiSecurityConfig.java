package com.ehealth.config;

import static com.ehealth.security.SecurityConstants.*;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ehealth.security.JWTAuthenticationFilter;
import com.ehealth.security.JWTAuthorizationFilter;
import com.ehealth.service.UserService;


/**
 * @author nagaraj 
 *
 */
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {


	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;


	public ApiSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
							 UserService userService) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
	}
	  @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
//		open access to specific urls  
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll() 
				.antMatchers(HttpMethod.POST, SIGN_IN_URL).permitAll()
				.antMatchers(HttpMethod.GET, TEST_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_API_DOC_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_CONFIG_UI_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_RESOURCE_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_SECURITY_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_UI_URL).permitAll()
				.antMatchers(HttpMethod.GET, SWAG_JARS_URL).permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
				.addFilter(new JWTAuthorizationFilter(authenticationManager())); 
		
		// open all the endpoints/no security
//		http.cors().and().csrf().disable().authorizeRequests().anyRequest().permitAll();
		 
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
}
