package com.eslam.poeauditor.domain.bundle;

import java.util.List;

import com.eslam.poeauditor.domain.ItemOverview;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemOverviewBundle {
    
    @JsonProperty("lines")
    private List<ItemOverview> itemOverviews;

    public Boolean hasOverviews() {
        return (this.itemOverviews != null && !this.itemOverviews.isEmpty());
    }
}
