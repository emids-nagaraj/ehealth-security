package com.ehealth.service;

import java.util.List;
import java.util.Optional;

import com.ehealth.enity.User;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(int userid);
    Optional<User> getUserByEmail(String emailId);
    User save(User user);
    User updateUser(User user);
    void deleteUser(int userid);
    boolean userExists(String title, String category);
}
