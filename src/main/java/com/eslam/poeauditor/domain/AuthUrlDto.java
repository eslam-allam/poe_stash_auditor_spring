package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class AuthUrlDto {

    @JsonProperty("state_code")
    private String stateCode;

    @JsonProperty("auth_link")
    private String authLink;


    public AuthUrlDto() {
    }

    public AuthUrlDto(String stateCode, String authLink) {
        this.stateCode = stateCode;
        this.authLink = authLink;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAuthLink() {
        return this.authLink;
    }

    public void setAuthLink(String authLink) {
        this.authLink = authLink;
    }

    public AuthUrlDto stateCode(String stateCode) {
        setStateCode(stateCode);
        return this;
    }

    public AuthUrlDto authLink(String authLink) {
        setAuthLink(authLink);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AuthUrlDto)) {
            return false;
        }
        AuthUrlDto authUrlDto = (AuthUrlDto) o;
        return Objects.equals(stateCode, authUrlDto.stateCode) && Objects.equals(authLink, authUrlDto.authLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateCode, authLink);
    }

    @Override
    public String toString() {
        return "{" +
            " stateCode='" + getStateCode() + "'" +
            ", authLink='" + getAuthLink() + "'" +
            "}";
    }
}
