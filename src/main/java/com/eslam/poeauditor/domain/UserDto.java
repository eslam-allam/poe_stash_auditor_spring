package com.eslam.poeauditor.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {
    
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("email")
    private String emailId;
    
    @JsonProperty("roles")
    private List<UserRoleDto> userRoles;
    
    @JsonProperty("state_codes")
    private List<UserStateDto> userStates;

}
