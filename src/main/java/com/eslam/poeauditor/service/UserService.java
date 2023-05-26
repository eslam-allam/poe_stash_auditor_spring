package com.eslam.poeauditor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserNotFoundException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.model.PoeUserDetails;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserRole;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.repository.UserRoleRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User createUser(User user, UserRoleCode userRoleCode) throws UserAlreadyExistsException, UserRoleNotFoundException {
        Optional<User> theUser = userRepository.findByEmailId(user.getEmailId());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException("A user with " +user.getEmailId() +" already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<UserRole> userRole = userRoleRepository.findByUserRoleCode(userRoleCode);

        if (!userRole.isPresent()) {
            throw new UserRoleNotFoundException(String.format("Could not find role with code: %s", userRoleCode));
        }

        user.addRole(userRole.get());
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getByEmailId(String emailId) {
        Optional<User> user = userRepository.findByEmailId(emailId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("Specified email is not associated with a user.");
        }

        return user.get();
    }

    public User getFromUserDetails(PoeUserDetails poeUserDetails) {
        Optional<User> user = userRepository.findByEmailId(poeUserDetails.getUsername());
        if (!user.isPresent()) {
            throw new UserNotFoundException("Could not find a user with the specified user details");
        }
        return user.get();
    }
}
