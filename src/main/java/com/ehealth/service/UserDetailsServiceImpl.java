package com.ehealth.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ehealth.dao.IUserDAO;

/**
 *@author Nagaraj
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
	private IUserDAO userDAO;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.ehealth.enity.User user = userDAO.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }


  //      System.out.println("hellooooooo---------");
        return new User(user.getEmail(), user.getPassword(), emptyList());
    }
}
