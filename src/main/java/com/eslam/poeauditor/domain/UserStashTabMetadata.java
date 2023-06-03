package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStashTabMetadata {
    
    @JsonProperty("public")
    private Boolean publicStash = false;

    @JsonProperty("folder")
    private Boolean folder = false;

    @JsonProperty("colour")
    private String colour;
}
