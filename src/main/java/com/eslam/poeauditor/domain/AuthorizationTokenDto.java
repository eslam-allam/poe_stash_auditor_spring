package com.eslam.poeauditor.domain;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import com.eslam.poeauditor.constant.Scope;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class AuthorizationTokenDto {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @Setter(AccessLevel.NONE)
    @JsonProperty("expires_in")
    private Date expiresIn;
    
    @JsonProperty("token_type")
    private String tokenType;

    @Setter(AccessLevel.NONE)
    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("username")
    private String username;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("refresh_token")
    private String refreshToken;

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = Date.from(Instant.now().plusSeconds(expiresIn));
    }

    public void setScope(String scope) {
        Optional<Scope> foundScope = Scope.getScope(scope);

        if (!foundScope.isPresent()) {
            throw new IllegalArgumentException(String.format("Provided scope %s is invalid", scope));
        }
        this.scope = foundScope.get();
    }

}
