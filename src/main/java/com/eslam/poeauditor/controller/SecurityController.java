package com.eslam.poeauditor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eslam.poeauditor.assembler.DtoAssembler;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.domain.AuthUrlDto;
import com.eslam.poeauditor.exception.UserAlreadyExistsException;
import com.eslam.poeauditor.exception.UserRoleNotFoundException;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.repository.UserRepository;
import com.eslam.poeauditor.request.AuthorizeRequest;
import com.eslam.poeauditor.request.JWTAuthenticationRequest;
import com.eslam.poeauditor.request.RegistrationRequest;
import com.eslam.poeauditor.service.JWTService;
import com.eslam.poeauditor.service.SecurityService;
import com.eslam.poeauditor.service.UserService;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {
    
    @Value("${poe.authorize.url}")
    private String poeAuthUrl;

    @Value("${poe.token.url}")
    private String poeTokenUrl;
    
    @Value("${redirect.uri}")
    private String redirectUri;
    
    @Value("${client.id}")
    private String clientId;
    
    @Value("${client.secret}")
    private String clientSecret;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;


    private final Logger logger = LogManager.getLogger(getClass());

    @GetMapping(value="/auth/url")
    public AuthUrlDto getAuthUrl(@RequestParam("scope") Scope scope) throws NoSuchAlgorithmException, URISyntaxException, AuthenticationException {
        
        User user = securityService.getLoggedInUser();

        UserState userState = securityService.createOrRefreshUserState(user);

        user.setUserState(userState);
        userRepository.save(user);

        AuthorizeRequest authorizeRequest = new AuthorizeRequest(userState)
        .clientId(clientId).scope(scope.getScopeName()).redirectUrl(redirectUri);

        return DtoAssembler.assemble(authorizeRequest, poeAuthUrl);
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        logger.info("generating authentication token");
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        return jwtService.generateToken(authRequest.getUserName());
    }

    @PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public User postMethodName(@RequestBody RegistrationRequest registrationRequest) throws UserAlreadyExistsException, UserRoleNotFoundException {
        User user = User.builder().userName(registrationRequest.getUserName())
        .emailId(registrationRequest.getEmail()).password(registrationRequest.getPassword()).build();
        return userService.createUser(user, UserRoleCode.BASE_USER);
    }
    
    @GetMapping(value="test")
    public Map<String, String> getMethodName(@RequestParam("code") String code, @RequestParam("state") String state) {
        return Collections.singletonMap(code, state);
    }
    
    
}
