package com.eslam.poeauditor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.repository.UserAuthorizationCodeRepository;

@Service
public class UserAuthorizationCodeService {

    @Autowired
    private UserAuthorizationCodeRepository userAuthorizationCodeRepository;
    
    public AuthorizationGrant saveAuthorizationCode(AuthorizationGrant userAuthorizationCode) {
        return userAuthorizationCodeRepository.save(userAuthorizationCode);
    }
}
