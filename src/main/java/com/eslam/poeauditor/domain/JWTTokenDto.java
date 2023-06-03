package com.eslam.poeauditor.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTTokenDto {
    
    @JsonProperty("issued_at")
    private Date issuedAt;

    @JsonProperty("expires_at")
    private Date expiresAt;

    @JsonProperty("token")
    private String token;

}
