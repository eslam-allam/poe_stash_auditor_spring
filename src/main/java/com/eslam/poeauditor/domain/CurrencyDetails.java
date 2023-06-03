package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDetails {
    
    @JsonProperty("icon")
    private String iconUrl;

    @JsonProperty("name")
    private String name;
}
