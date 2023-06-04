package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOverview {

    @JsonProperty("name")
    @JsonAlias("currencyTypeName")
    private String name;
    
    @JsonProperty("chaosValue")
    @JsonAlias("chaosEquivalent")
    private Double chaosValue;
    
    @JsonProperty("detailsId")
    private String detailsId;

}
