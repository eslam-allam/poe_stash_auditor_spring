package com.eslam.poeauditor.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.eslam.poeauditor.constant.GrantType;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.domain.AuthorizationTokenDto;
import com.eslam.poeauditor.exception.UserStateNotFoundException;
import com.eslam.poeauditor.model.PoeUserDetails;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.repository.UserStateRepository;
import com.eslam.poeauditor.request.AuthorizeRequest;
import com.eslam.poeauditor.request.TokenRequest;
import com.eslam.poeauditor.security.PkceUtil;

import jakarta.annotation.PostConstruct;

@Service
public class SecurityService {

    private final Logger logger = LogManager.getLogger(getClass());

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
    private UserStateRepository userStateRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        
    }

    public UserState createOrRefreshUserState(User user, Scope scope) throws NoSuchAlgorithmException {
        if (user == null) {
            throw new IllegalArgumentException("Provided user is null");
        }

        List<UserState> userStates = user.getUserStates();
        Optional<UserState> userState = getWithScope(userStates, scope);
        UserState refreshedState;

        if (!userState.isPresent()) {
            refreshedState = generateUserState(scope, user);
        } else {
            refreshedState = refreshUserState(userState.get());
        }
        return refreshedState;
    }


    private UserState generateUserState(Scope scope, User user) throws NoSuchAlgorithmException{
        String codeVerifier = PkceUtil.generateCodeVerifier();
        String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
        String state = PkceUtil.generateRandomAlphanumeric(32);

        UserState userState = UserState.builder().state(state).codeVerifier(codeVerifier)
        .codeChallenge(codeChallenge).scope(scope).user(user).build();
        return userStateRepository.save(userState);
    }
    
    private UserState refreshUserState(UserState userState) throws NoSuchAlgorithmException{
        String codeVerifier = PkceUtil.generateCodeVerifier();
        String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
        userState.setCodeChallenge(codeChallenge);
        userState.setCodeVerifier(codeVerifier);
        return userStateRepository.save(userState);
    }

    public User getLoggedInUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated.");
        }

        Object principal = authentication.getPrincipal();


        if (!(principal instanceof UserDetails)) {
            throw new AuthenticationException("Returned principal is not a user.");
        }
        return userService.getFromUserDetails((PoeUserDetails) principal);
    }

    public UserState getUserStateByState(String state) throws UserStateNotFoundException {
        Optional<UserState> userState = userStateRepository.findByState(state);

        if (!userState.isPresent()) {
            throw new UserStateNotFoundException("Could not find specified state");
        }

        return userState.get();
    }

    public UserState saveUserState(UserState userState) {
        return userStateRepository.save(userState);
    }

    public AuthorizeRequest generateAuthorizedRequest(UserState userState, Scope scope) {
        return AuthorizeRequest.builder().userState(userState).targetUrl(poeAuthUrl)
        .clientId(clientId).scope(scope).redirectUrl(redirectUri).build();
    }

    public TokenRequest generateTokenRequest(UserState userState, Scope scope, String code) {
        return TokenRequest.builder().clientId(clientId).clientSecret(clientSecret)
        .grantType(GrantType.AUTHORIZATION_CODE).code(code).redirectUrl(redirectUri)
        .scope(scope).codeVerifier(userState.getCodeVerifier()).build();
    }

    private Optional<UserState> getWithScope(List<UserState> userStates, Scope scope) {
        if (userStates == null) {
            return Optional.empty();
        }
        return userStates.stream().filter(userState -> userState.getScope().equals(scope)).findFirst();
    }

    public AuthorizationTokenDto getAuthorizationToken(TokenRequest tokenRequest) throws RestClientException, IllegalArgumentException, IllegalAccessException {
        logger.info("Generating user authorization token...");
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(tokenRequest.toMap(), httpHeaders);
        
        return restTemplate.postForObject(poeTokenUrl, request, AuthorizationTokenDto.class);
    }

}
