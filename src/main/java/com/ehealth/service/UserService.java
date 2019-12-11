package com.ehealth.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ehealth.dao.IUserDAO;
import com.ehealth.dto.UserDTO;
import com.ehealth.enity.User;
import com.ehealth.util.EHealthConstants;

@Service
public class UserService implements IUserService {

	private static final String USER_PREFIX = EHealthConstants.USER_PREFIX + "_";
	private static final ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	public List<UserDTO> getUsersEmailList() {

		java.lang.reflect.Type targetListType = new TypeToken<List<UserDTO>>() {
		}.getType();

		List<UserDTO> usersEmailList = modelMapper.map(userDAO.getAllUsers(), targetListType);
		return usersEmailList;
	}

	public Boolean isAdmin(String email) {
		return getUserByEmail(email).get().getIsAdmin();
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	public User getUserById(int userid) {
		return userDAO.getUserById(userid);
	}

	@Override
	public  Optional<User> getUserByEmail(String emailId) {
		User user = userDAO.getUserByEmail(emailId);
		Optional<User> userOptional = Optional.of(user);
		return userOptional;
	}

	@Override
	public User save(User user) {
		 
//		userDTO.setId(USER_PREFIX + UUID.randomUUID());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setCreatedOn(LocalDateTime.now());
		return userDAO.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(int userid) {
		userDAO.deleteUser(userid);
	}

	@Override
	public boolean userExists(String title, String category) {
		// TODO Auto-generated method stub
		return false;
	}
}
