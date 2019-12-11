package com.ehealth.dao;
 
import java.util.List;

import com.ehealth.enity.User;
  
public interface IUserDAO {
    List<User> getAllUsers();
    User getUserById(int userid);
    User getUserByEmail(String emailId);
    User save(User user);
    User updateUser(User user);
    void deleteUser(int userid);
    boolean userExists(String title, String category);
} 