package com.eslam.poeauditor.domain.bundle;

import java.util.List;

import com.eslam.poeauditor.domain.UserStashTab;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserStashTabBundle {
    
    @JsonProperty("stashes")
    private List<UserStashTab> userStashTabs;
}
