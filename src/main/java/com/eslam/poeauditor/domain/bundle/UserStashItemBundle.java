package com.eslam.poeauditor.domain.bundle;

import java.util.List;
import java.util.Map;

import com.eslam.poeauditor.domain.UserStashItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStashItemBundle {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ItemStash {

        @JsonProperty("items")
        private List<UserStashItem> userStashItems;
    }
    
    @JsonProperty("stash")
    private ItemStash itemStash;
}
