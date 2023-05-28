package com.eslam.poeauditor.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.eslam.poeauditor.assembler.ModelAssembler;
import com.eslam.poeauditor.domain.AuthorizationTokenDto;
import com.eslam.poeauditor.exception.UserStateNotFoundException;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.request.TokenRequest;
import com.eslam.poeauditor.service.SecurityService;
import com.eslam.poeauditor.service.UserAuthorizationCodeService;
import com.eslam.poeauditor.service.UserService;

@Controller
public class LandingController {

    private final Logger logger = LogManager.getLogger(getClass());
    

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorizationCodeService userAuthorizationCodeService;

    @GetMapping("/poeaccess")
    public String showUserList(
        @RequestParam("code") String code, 
        @RequestParam("state") String state) throws UserStateNotFoundException, RestClientException, IllegalArgumentException, IllegalAccessException {
   
        UserState userState = securityService.getUserStateByState(state);
        TokenRequest tokenRequest = securityService.generateTokenRequest(userState, userState.getScope(), code);
        AuthorizationTokenDto authorizationTokenDto = securityService.getAuthorizationToken(tokenRequest);
        
        User user = userState.getUser();
        user.setUserName(authorizationTokenDto.getUsername());
        user = userService.saveUser(user);
        
        AuthorizationGrant userAuthorizationCode = ModelAssembler.assemble(authorizationTokenDto, user);

        userAuthorizationCodeService.saveAuthorizationCode(userAuthorizationCode);
        
        return "redirect:https://google.com";
    }
}
