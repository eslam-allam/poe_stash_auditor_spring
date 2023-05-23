package com.eslam.poeauditor.request;

import com.eslam.poeauditor.domain.AuthUrlDto;
import com.eslam.poeauditor.model.UserState;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class AuthorizeRequest {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("redirect_uri")
    private String redirectUrl;


    @JsonProperty("response_type")
    private String responseType = "code";

    @JsonProperty("state")
    private final String state;

    @JsonProperty("code_challenge")
    private final String codeChallenge;

    @JsonProperty("code_challenge_method")
    private String codeChallengeMethod = "S256";



    public AuthorizeRequest(UserState userState) {
        this.state = userState.getState();
        this.codeChallenge = userState.getCodeChallenge();
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getState() {
        return this.state;
    }

    public String getCodeChallenge() {
        return this.codeChallenge;
    }

    public String getCodeChallengeMethod() {
        return this.codeChallengeMethod;
    }

    public void setCodeChallengeMethod(String codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
    }

    public AuthorizeRequest clientId(String clientId) {
        setClientId(clientId);
        return this;
    }

    public AuthorizeRequest scope(String scope) {
        setScope(scope);
        return this;
    }

    public AuthorizeRequest redirectUrl(String redirectUrl) {
        setRedirectUrl(redirectUrl);
        return this;
    }

    public AuthorizeRequest responseType(String responseType) {
        setResponseType(responseType);
        return this;
    }

    public AuthorizeRequest codeChallengeMethod(String codeChallengeMethod) {
        setCodeChallengeMethod(codeChallengeMethod);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AuthorizeRequest)) {
            return false;
        }
        AuthorizeRequest authorizeRequest = (AuthorizeRequest) o;
        return Objects.equals(clientId, authorizeRequest.clientId) && Objects.equals(scope, authorizeRequest.scope) && Objects.equals(redirectUrl, authorizeRequest.redirectUrl) && Objects.equals(responseType, authorizeRequest.responseType) && Objects.equals(state, authorizeRequest.state) && Objects.equals(codeChallenge, authorizeRequest.codeChallenge) && Objects.equals(codeChallengeMethod, authorizeRequest.codeChallengeMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, scope, redirectUrl, responseType, state, codeChallenge, codeChallengeMethod);
    }

    @Override
    public String toString() {
        return "{" +
            " clientId='" + getClientId() + "'" +
            ", scope='" + getScope() + "'" +
            ", redirectUrl='" + getRedirectUrl() + "'" +
            ", responseType='" + getResponseType() + "'" +
            ", state='" + getState() + "'" +
            ", codeChallenge='" + getCodeChallenge() + "'" +
            ", codeChallengeMethod='" + getCodeChallengeMethod() + "'" +
            "}";
    }
    
    
    
}
