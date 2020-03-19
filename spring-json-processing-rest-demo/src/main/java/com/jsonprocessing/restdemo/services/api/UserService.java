package com.jsonprocessing.restdemo.services.api;

import com.jsonprocessing.restdemo.models.User;
import java.util.Collection;

public interface UserService {
    Collection<User> getUser();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User addUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getUsersCount();
}
