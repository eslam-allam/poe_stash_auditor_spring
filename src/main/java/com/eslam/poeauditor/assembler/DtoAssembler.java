package com.eslam.poeauditor.assembler;

import java.net.URISyntaxException;

import org.apache.hc.core5.net.URIBuilder;
import com.eslam.poeauditor.domain.AuthUrlDto;
import com.eslam.poeauditor.request.AuthorizeRequest;

public final class DtoAssembler {

    private DtoAssembler() {
        
    }

    public static AuthUrlDto assemble(AuthorizeRequest authorizeRequest, String baseUrl) throws URISyntaxException {
        String uri = new URIBuilder(baseUrl).addParameter("client_id", authorizeRequest.getClientId())
        .addParameter("response_type", authorizeRequest.getResponseType())
        .addParameter("scope", authorizeRequest.getScope()).addParameter("state", authorizeRequest.getState())
        .addParameter("redirect_uri", authorizeRequest.getRedirectUrl())
        .addParameter("code_challenge", authorizeRequest.getCodeChallenge())
        .addParameter("code_challenge_method", authorizeRequest.getCodeChallengeMethod()).build().toString();
        return new AuthUrlDto(authorizeRequest.getState(), uri);
    }
    
}
