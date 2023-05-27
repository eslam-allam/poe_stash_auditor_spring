package com.eslam.poeauditor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/users/list")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    

}
