package com.eslam.poeauditor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eslam.poeauditor.model.UserAuthorizationCode;
import com.eslam.poeauditor.repository.UserAuthorizationCodeRepository;

@Service
public class UserAuthorizationCodeService {

    @Autowired
    private UserAuthorizationCodeRepository userAuthorizationCodeRepository;
    
    public UserAuthorizationCode saveAuthorizationCode(UserAuthorizationCode userAuthorizationCode) {
        return userAuthorizationCodeRepository.save(userAuthorizationCode);
    }
}
