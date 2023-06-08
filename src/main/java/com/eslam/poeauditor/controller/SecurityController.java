package com.eslam.poeauditor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eslam.poeauditor.assembler.DtoAssembler;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.domain.AuthUrlDto;
import com.eslam.poeauditor.domain.JWTTokenDto;
import com.eslam.poeauditor.domain.UserDto;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.mapper.UserMapper;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.request.AuthorizeRequest;
import com.eslam.poeauditor.request.JWTAuthenticationRequest;
import com.eslam.poeauditor.request.RegistrationRequest;
import com.eslam.poeauditor.service.JWTService;
import com.eslam.poeauditor.service.SecurityService;
import com.eslam.poeauditor.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    private final Logger logger = LogManager.getLogger(getClass());

    @SecurityRequirement(name = "base-user")
    @Operation(description = "Get path of exile login url. This url is redirected to in order to link the user's account")
    @GetMapping(value="/auth/url")
    public AuthUrlDto getAuthUrl(@RequestParam("scope") Scope scope) throws NoSuchAlgorithmException, URISyntaxException, AuthenticationException {
        
        User user = securityService.getLoggedInUser();

        UserState userState = securityService.createOrRefreshUserState(user, scope);

        AuthorizeRequest authorizeRequest = securityService.generateAuthorizedRequest(userState, scope);

        return DtoAssembler.assemble(authorizeRequest);
    }

    @Operation(description = "Log-in and get JWT token.")
    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public JWTTokenDto getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        logger.info("generating authentication token");
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        return jwtService.generateToken(authRequest.getUserName());
    }

    @Operation(description = "Register new user.")
    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto postMethodName(@RequestBody @Validated RegistrationRequest registrationRequest) throws UserAlreadyExistsException, UserRoleNotFoundException {
        User user = User.builder().emailId(registrationRequest.getEmail())
        .password(registrationRequest.getPassword()).build();
        return userMapper.assemble(userService.createUser(user, UserRoleCode.BASE_USER));
    }

}
