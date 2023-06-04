package com.eslam.poeauditor.domain;

import java.util.List;

import com.eslam.poeauditor.constant.StashType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserStashTab {
    
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private StashType type;

    @JsonProperty("metadata")
    private UserStashTabMetadata metadata;

    @JsonProperty("children")
    private List<UserStashTab> children;

    public void setType(String type) {
        this.type = StashType.typeSearch(type);
    }
}
