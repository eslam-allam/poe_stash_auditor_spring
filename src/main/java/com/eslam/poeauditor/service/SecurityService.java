package com.eslam.poeauditor.service;

import java.security.NoSuchAlgorithmException;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.model.PoeUserDetails;
import com.eslam.poeauditor.model.User;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.repository.UserStateRepository;
import com.eslam.poeauditor.security.PkceUtil;

@Service
public class SecurityService {
    
    @Autowired
    private UserStateRepository userStateRepository;

    @Autowired
    private UserService userService;

    public UserState createOrRefreshUserState(User user) throws NoSuchAlgorithmException {
        if (user == null) {
            throw new IllegalArgumentException("Provided user is null");
        }

        if (user.getUserState() == null) {
            return generateUserState();
        }

        return refreshUserState(user.getUserState());
    }


    private UserState generateUserState() throws NoSuchAlgorithmException{
        String codeVerifier = PkceUtil.generateCodeVerifier();
        String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
        String state = PkceUtil.generateRandomAlphanumeric(32);

        UserState userState = new UserState().state(state).codeVerifier(codeVerifier).codeChallenge(codeChallenge);
        return userStateRepository.save(userState);
    }
    
    private UserState refreshUserState(UserState userState) throws NoSuchAlgorithmException{
        String codeVerifier = PkceUtil.generateCodeVerifier();
        String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
        userState.codeChallenge(codeChallenge).codeVerifier(codeVerifier);
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

}
