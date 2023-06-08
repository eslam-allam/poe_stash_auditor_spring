package com.eslam.poeauditor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.eslam.poeauditor.domain.UserDto;
import com.eslam.poeauditor.mapper.UserMapper;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "admin")
public class AdminController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Operation(description = "List all registered users.")
    @GetMapping(value="/users/list")
    public List<UserDto> getAllUsers() {
        return userMapper.assembleUserDtoList(userRepository.findAll());
    }
    

}
