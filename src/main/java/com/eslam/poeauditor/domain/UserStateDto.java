package com.eslam.poeauditor.domain;

import com.eslam.poeauditor.constant.Scope;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserStateDto {
    
    @JsonProperty("state_code")
    private String state;

    @JsonProperty("scope")
    private Scope scope;

}
