package com.eslam.poeauditor.jobs.scheduled;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eslam.poeauditor.constant.ItemCategory;
import com.eslam.poeauditor.constant.Scope;
import com.eslam.poeauditor.constant.UserRoleCode;
import com.eslam.poeauditor.domain.ItemOverview;
import com.eslam.poeauditor.domain.LeagueDto;
import com.eslam.poeauditor.domain.bundle.ItemOverviewBundle;
import com.eslam.poeauditor.model.AuthorizationGrant;
import com.eslam.poeauditor.service.ItemService;
import com.eslam.poeauditor.service.PoeApiService;
import com.eslam.poeauditor.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
@DependsOn("databaseInitializer")
public class PriceFetch {

    private final Logger logger = LogManager.getLogger(getClass());

    @Value("${poe.ninja.api.base.url}")
    private String poeNinjaBaseUrl;
    
    private final List<ItemCategory> itemCategories = ItemCategory.getCategories();
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired 
    private ItemService itemService;

    @Autowired 
    private UserService userService;

    @Autowired
    private PoeApiService poeApiService;

    @PostConstruct
    @Scheduled(fixedDelayString = "${price.fetch.job.delay.ms}", initialDelayString = "${price.fetch.job.delay.ms}")
    private void fetchPrices() {
        logger.info("Fetching prices from POE Ninja...");
        Instant start = Instant.now();
        Optional<AuthorizationGrant> authorizationGrant = userService.getByUserRoleCode(UserRoleCode.SUPER_MAIN_ADMIN)
        .getUserAuthorizationGrants().stream().filter(grant -> grant.getScope().equals(Scope.LEAGUES)).findAny();
        
        if (authorizationGrant.isEmpty()) {
            throw new IllegalStateException("Could not find authorization grant for leagues service.");
        }
        List<LeagueDto> leagues = poeApiService.requestLeagueList(authorizationGrant.get()).getLeagues()
        .stream().filter(league -> league.getLeagueRules().stream()
        .allMatch(rule -> rule.getLeagueRule().isEnabled())).toList();

        itemService.setLeagues(leagues);

        itemService.getLeagues().parallelStream().map(LeagueDto::getLeagueId).forEach(this::addOverviews);
        
        Duration duration = Duration.between(start ,Instant.now());

        logger.info("Fetched prices successfully in: {}", 
        DurationFormatUtils.formatDurationHMS(duration.toMillis()));
        
    }

    private void addOverviews(String league) {
        logger.info("Fetching price data for league: {}", league);
        List<ItemOverview> tempOverviews = new ArrayList<>();
        itemCategories.parallelStream().forEach(category -> {
            String finalUrl = UriComponentsBuilder.fromHttpUrl(poeNinjaBaseUrl)
                    .pathSegment(category.getOverViewType().getStringValue()).queryParam("league", league)
                    .queryParam("type", category.getStringValue()).encode().toUriString();

                    logger.info("fetching item information from {}", finalUrl);
                    ItemOverviewBundle itemOverviewBundle = restTemplate.getForObject(finalUrl, 
                    ItemOverviewBundle.class);

                    if (itemOverviewBundle != null && itemOverviewBundle.hasOverviews()) {
                        tempOverviews.addAll(itemOverviewBundle.getItemOverviews());
                    }
                        
        });
        itemService.putItemOverview(league, tempOverviews);
    }
}
