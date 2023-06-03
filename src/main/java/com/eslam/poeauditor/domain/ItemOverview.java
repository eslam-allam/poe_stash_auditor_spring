package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOverview {

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("icon")
    private String iconUrl;
    
    @JsonProperty("chaosValue")
    private Double chaosValue;
    
    @JsonProperty("detailsId")
    private String detailsId;

}
