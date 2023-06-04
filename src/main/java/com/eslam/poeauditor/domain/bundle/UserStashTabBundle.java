package com.eslam.poeauditor.domain.bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eslam.poeauditor.constant.StashType;
import com.eslam.poeauditor.domain.UserStashTab;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserStashTabBundle {
    
    @JsonProperty("stashes")
    private List<UserStashTab> userStashTabs;

    public List<UserStashTab> getUserStashTabs(List<StashType> stashTypes) {
        return userStashTabs.parallelStream().filter(stash -> stashTypes.parallelStream().anyMatch(s -> s.equals(stash.getType())))
        .collect(Collectors.toList());
    }
    
    public List<UserStashTab> getUserStashTabs(List<StashType> stashTypes, Boolean expandFolders) {
        List<UserStashTab> expandedUserStashTabs = new ArrayList<>();
        if (Boolean.TRUE.equals(expandFolders)) {
            this.userStashTabs.parallelStream().forEach(s -> addOrExpandStashTabs(s, expandedUserStashTabs));
        }
        return expandedUserStashTabs.parallelStream().filter(stash -> stashTypes.parallelStream().anyMatch(s -> s.equals(stash.getType())))
        .collect(Collectors.toList());
    }

    private void addOrExpandStashTabs(UserStashTab userStashTab, List<UserStashTab> target) {
        if (Boolean.TRUE.equals(userStashTab.getMetadata().getFolder())) {
            userStashTab.getChildren().parallelStream().forEach(s -> addOrExpandStashTabs(s, target));
        }
        else {
            target.add(userStashTab);
        }
    }
}
