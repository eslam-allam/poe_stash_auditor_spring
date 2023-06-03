package com.eslam.poeauditor.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eslam.poeauditor.domain.UserStashTab;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.service.SecurityService;
import com.eslam.poeauditor.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "base-user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
    
    @GetMapping(value="/stash/list")
    public List<UserStashTab> getUserStashTabs(@RequestParam("league_name") String league) throws AuthenticationException {
        User user = securityService.getLoggedInUser();
        return userService.getUserStashTabs(user, league);

    }
    
}
