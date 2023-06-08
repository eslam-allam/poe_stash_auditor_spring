package com.eslam.poeauditor.domain;

import com.eslam.poeauditor.constant.LeagueRuleType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LeagueRuleDto {
    
    @JsonProperty("id")
    private LeagueRuleType leagueRule;

    @JsonProperty("name")
    private String leagueName;

    @JsonProperty("description")
    private String description;

    public void setLeagueRule(String leagueRule) {
        this.leagueRule = LeagueRuleType.leagueSearch(leagueRule);
    }
}
