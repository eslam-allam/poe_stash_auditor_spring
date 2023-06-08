package com.eslam.poeauditor.request;

import com.eslam.poeauditor.constraint.MatchingValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@MatchingValue(source = "password", target = "confirmPassword", message = "password and confirm password don`t match")
public class RegistrationRequest {

    @Email
    @NotEmpty
    @JsonProperty("email")
    private String email;

    @NotEmpty
    @JsonProperty("password")
    private String password;
    
    @NotEmpty
    @JsonProperty("confirm_password")
    private String confirmPassword;
}
