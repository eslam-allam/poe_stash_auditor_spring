package com.eslam.poeauditor.domain.bundle;

import java.util.List;

import com.eslam.poeauditor.domain.CurrencyDetails;
import com.eslam.poeauditor.domain.CurrencyOverview;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CurrencyOverviewBundle {
    
    @JsonProperty("lines")
    private List<CurrencyOverview> currencyOverviews;

    @JsonProperty("currencyDetails")
    private List<CurrencyDetails> details;

    public Boolean hasOverviews() {
        return (this.currencyOverviews != null && !this.currencyOverviews.isEmpty())
        && (this.details != null && !this.details.isEmpty());
    }

    public List<CurrencyOverview> assembleOverviews() {
        this.currencyOverviews.stream().forEach(
            c -> c.setIconUrl(this.details.stream().filter(
                d -> d.getName().equals(c.getCurrencyTypeName())).findFirst().get().getIconUrl()));
        return this.currencyOverviews;
    }
}
