package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyOverview {
    
    @JsonProperty("currencyTypeName")
    private String currencyTypeName;

    @JsonProperty("detailsId")
    private String detailsId;

    @JsonProperty("chaosEquivalent")
    private Double chaosEquivalent;

    @JsonProperty("icon_url")
    private String iconUrl;
}
