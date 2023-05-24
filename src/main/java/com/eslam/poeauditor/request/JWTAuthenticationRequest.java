package com.eslam.poeauditor.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class JWTAuthenticationRequest {
    
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;
}
