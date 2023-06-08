package com.eslam.poeauditor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.eslam.poeauditor.domain.ItemOverview;
import com.eslam.poeauditor.domain.LeagueDto;

@Service
public class ItemService {
    
    private List<LeagueDto> leagues;
    private Map<String ,List<ItemOverview>> itemOverviews = new HashMap<>();

    public void putItemOverview(String league ,List<ItemOverview> itemOverviews) {
        this.itemOverviews.put(league, itemOverviews);
    }
    
    public void setItemOverviews(Map<String, List<ItemOverview>> itemOverviews) {
        this.itemOverviews = itemOverviews;
    }

    public void setLeagues(List<LeagueDto> leagues) {
        this.leagues = leagues;
    }

    public Map<String, List<ItemOverview>> getItemOverviews() {
        return itemOverviews;
    }
    
    public List<ItemOverview> getItemOverview(String league) {
        return itemOverviews.get(league);
    }

    public List<LeagueDto> getLeagues() {
        return leagues;
    }
}
