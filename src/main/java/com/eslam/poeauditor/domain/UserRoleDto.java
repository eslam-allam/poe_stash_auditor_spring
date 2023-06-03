package com.eslam.poeauditor.domain;

import com.eslam.poeauditor.constant.UserRoleCode;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserRoleDto {
    
    @JsonProperty("role_name")
    private UserRoleCode userRoleCode;

    @JsonProperty("description")
    private String description;
    

}
