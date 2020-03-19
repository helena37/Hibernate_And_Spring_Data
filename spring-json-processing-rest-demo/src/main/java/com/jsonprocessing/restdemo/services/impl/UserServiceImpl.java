package com.jsonprocessing.restdemo.services.impl;

import com.jsonprocessing.restdemo.dao.UserRepository;
import com.jsonprocessing.restdemo.models.User;
import com.jsonprocessing.restdemo.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> getUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with ID %d not found!",
                        id
                ))
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with username %s not found!",
                        username
                ))
        );
    }

    @Override
    public User addUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        return null;
    }

    @Override
    public long getUsersCount() {
        return 0;
    }
}
