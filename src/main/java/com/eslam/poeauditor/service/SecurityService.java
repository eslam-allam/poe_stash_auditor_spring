package com.eslam.poeauditor.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.repository.UserStateRepository;
import com.eslam.poeauditor.security.PkceUtil;

@Service
public class SecurityService {
    
    @Autowired
    private UserStateRepository userStateRepository;

    public UserState generateUserState() throws NoSuchAlgorithmException{
        String codeVerifier = PkceUtil.generateCodeVerifier();
        String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
        String state = PkceUtil.generateRandomAlphanumeric(32);

        UserState userState = new UserState().state(state).codeVerifier(codeVerifier).codeChallenge(codeChallenge);
        return userStateRepository.save(userState);
    }
}
