package com.eslam.poeauditor.jobs.scheduled;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eslam.poeauditor.constant.ItemCategory;
import com.eslam.poeauditor.constant.OverViewType;
import com.eslam.poeauditor.domain.CurrencyOverview;
import com.eslam.poeauditor.domain.ItemOverview;
import com.eslam.poeauditor.domain.bundle.CurrencyOverviewBundle;
import com.eslam.poeauditor.domain.bundle.ItemOverviewBundle;

import jakarta.annotation.PostConstruct;

@Component
public class PriceFetch {

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${poe.ninja.api.base.url}")
    private String poeNinjaBaseUrl;
    
    private final List<ItemCategory> itemCategories = ItemCategory.getCategories();
    private final RestTemplate restTemplate = new RestTemplate();

    private List<String> leagues;
    private Map<String ,List<CurrencyOverview>> currencyOverviews = new HashMap<>();
    private Map<String ,List<ItemOverview>> itemOverviews = new HashMap<>();


    @PostConstruct
    @Scheduled(fixedDelayString = "${price.fetch.job.delay.ms}")
    private void fetchPrices() {
        logger.info("Fetching prices from POE Ninja...");
        Instant start = Instant.now();

        // TODO: Substitute with API call
        this.leagues = Arrays.asList("Crucible", "standard", "Forbidden Sanctum");

        this.leagues.parallelStream().forEach(this::addOverviews);
        
        Duration duration = Duration.between(start ,Instant.now());

        logger.info("Fetched prices successfully in: {}", 
        DurationFormatUtils.formatDurationHMS(duration.toMillis()));
    }

    private void addOverviews(String league) {
        logger.info("Fetching price data for league: {}", league);
        itemCategories.parallelStream().forEach(category -> {
            String finalUrl = UriComponentsBuilder.fromHttpUrl(poeNinjaBaseUrl)
                    .pathSegment(category.getOverViewType().getStringValue()).queryParam("league", league)
                    .queryParam("type", category.getStringValue()).encode().toUriString();

                if (category.getOverViewType().equals(OverViewType.CURRENCY)){
                    logger.info("fetching currency information from {}", finalUrl);
                    CurrencyOverviewBundle currencyOverviewBundle = restTemplate.getForObject(finalUrl,
                    CurrencyOverviewBundle.class);

                    if (currencyOverviewBundle != null && currencyOverviewBundle.hasOverviews()) {
                        this.currencyOverviews.put(league, currencyOverviewBundle.assembleOverviews());
                    }
                }
                else if (category.getOverViewType().equals(OverViewType.ITEM)){
                    logger.info("fetching item information from {}", finalUrl);
                    ItemOverviewBundle itemOverviewBundle = restTemplate.getForObject(finalUrl, 
                    ItemOverviewBundle.class);

                    if (itemOverviewBundle != null && itemOverviewBundle.hasOverviews()) {
                        this.itemOverviews.put(league, itemOverviewBundle.getItemOverviews());
                    }
                }            
        });
    }

    public List<String> getLeagues() {
        return leagues;
    }

    public List<CurrencyOverview> getCurrencyOverview(String league) {
        return currencyOverviews.get(league);
    }

    public List<ItemOverview> getItemOverview(String league) {
        return itemOverviews.get(league);
    }
}
