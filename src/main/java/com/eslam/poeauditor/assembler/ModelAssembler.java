package com.eslam.poeauditor.assembler;

import com.eslam.poeauditor.domain.AuthorizationTokenDto;
import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.model.User;

public class ModelAssembler {
    
    private ModelAssembler () {

    }

    public static AuthorizationGrant assemble(AuthorizationTokenDto authorizationTokenDto, User user) {
        return AuthorizationGrant.builder()
        .accessToken(authorizationTokenDto.getAccessToken()).expiresAt(authorizationTokenDto.getExpiresAt())
        .refreshToken(authorizationTokenDto.getRefreshToken()).scope(authorizationTokenDto.getScope())
        .sub(authorizationTokenDto.getSub()).tokenType(authorizationTokenDto.getTokenType()).user(user)
        .build();
    }
}
