package com.eslam.poeauditor.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.repository.UserRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) throws UserAlreadyExistsException {
        Optional<User> theUser = userRepository.findByEmailId(user.getEmailId());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException("A user with " +user.getEmailId() +" already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        return userRepository.save(user);
    }

}
