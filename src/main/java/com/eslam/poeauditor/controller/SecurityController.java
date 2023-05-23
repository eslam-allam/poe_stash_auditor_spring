package com.eslam.poeauditor.controller;

import org.springframework.web.bind.annotation.RestController;

import com.eslam.poeauditor.DtoAssembler;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.domain.AuthUrlDto;
import com.eslam.poeauditor.model.UserState;
import com.eslam.poeauditor.request.AuthorizeRequest;
import com.eslam.poeauditor.security.PkceUtil;
import com.eslam.poeauditor.service.SecurityService;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
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

    @GetMapping(value="/auth/url")
    public AuthUrlDto getAuthUrl(@RequestParam("scope") Scope scope) throws NoSuchAlgorithmException, URISyntaxException {
        
        UserState userState = securityService.generateUserState();
        AuthorizeRequest authorizeRequest = new AuthorizeRequest(userState)
        .clientId(clientId).scope(scope.getScopeName()).redirectUrl(redirectUri);

        return DtoAssembler.assemble(authorizeRequest, poeAuthUrl);
    }
    
}
