package com.eslam.poeauditor.domain.bundle;

import java.util.List;

import com.eslam.poeauditor.domain.LeagueDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LeagueBundle {
    
    @JsonProperty("leagues")
    private List<LeagueDto> leagues;
}
