package com.eslam.poeauditor.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"stackSize" ,"chaos_value"})
public class UserStashItem {
    
    @JsonProperty("stackSize")
    private Integer stackSize = 1;

    @JsonProperty("typeLine")
    private String typeLine;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("chaos_value")
    private Double chaosValue;
    
    @JsonProperty("total_value")
    private Double totalValue;

    public void setChaosValue(Double chaosValue){
        this.chaosValue = chaosValue;
        this.totalValue = chaosValue * stackSize;
    }
}
