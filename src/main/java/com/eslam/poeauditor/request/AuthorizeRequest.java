package com.eslam.poeauditor.request;

import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.model.UserState;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthorizeRequest {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("redirect_uri")
    private String redirectUrl;

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("response_type")
    private String responseType = "code";

    @JsonProperty("state")
    private final String state;

    @JsonProperty("code_challenge")
    private final String codeChallenge;

    @JsonProperty("code_challenge_method")
    private String codeChallengeMethod = "S256";

    @Builder
    public AuthorizeRequest(String clientId, Scope scope, String redirectUrl, 
    String targetUrl, UserState userState) {
        this.clientId = clientId;
        this.scope = scope;
        this.redirectUrl = redirectUrl;
        this.baseUrl = targetUrl;
        this.state = userState.state();
        this.codeChallenge = userState.codeChallenge();
    }
}
