package com.eslam.poeauditor.domain;

import java.util.Date;
import java.util.List;

import com.eslam.poeauditor.constant.LeagueRealm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LeagueDto {
    
    @JsonProperty("id")
    private String leagueId;

    @JsonProperty("realm")
    private LeagueRealm leagueRealm;

    @JsonProperty("url")
    private String url;

    @JsonProperty("startAt")
    private Date startAt;

    @JsonProperty("endAt")
    private Date endAt;

    @JsonProperty("description")
    private String description;

    @JsonProperty("registerAt")
    private Date registerAt;

    @JsonProperty("rules")
    private List<LeagueRuleDto> leagueRules;

    public void setLeagueRealm(String leagueRealm) {
        this.leagueRealm = LeagueRealm.realmSearch(leagueRealm);
    }

}
