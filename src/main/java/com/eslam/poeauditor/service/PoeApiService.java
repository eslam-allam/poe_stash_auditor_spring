package com.eslam.poeauditor.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.eslam.poeauditor.constant.PoeApiPath;
import com.eslam.poeauditor.domain.bundle.LeagueBundle;
import com.eslam.poeauditor.domain.bundle.UserStashItemBundle;
import com.eslam.poeauditor.domain.bundle.UserStashTabBundle;
import com.eslam.poeauditor.model.AuthorizationGrant;

@Service
public class PoeApiService {

    private final Logger logger = LogManager.getLogger(getClass());

    
    @Value("${poe.api.url}")
    private String poeApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public UserStashTabBundle requestUserStashTabs(AuthorizationGrant authorizationGrant, String league) {
        String url = UriComponentsBuilder.fromHttpUrl(poeApiUrl).pathSegment(PoeApiPath.STASH.getStringValue())
        .pathSegment(league).toUriString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authorizationGrant.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserStashTabBundle.class).getBody();
    }

    public UserStashItemBundle requestUserStashItems(AuthorizationGrant authorizationGrant, String league, String stashId) {
        String url = UriComponentsBuilder.fromHttpUrl(poeApiUrl).pathSegment(PoeApiPath.STASH.getStringValue())
        .pathSegment(league).pathSegment(stashId).toUriString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authorizationGrant.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserStashItemBundle.class).getBody();
    }

    public LeagueBundle requestLeagueList(AuthorizationGrant authorizationGrant) {
        String url = UriComponentsBuilder.fromHttpUrl(poeApiUrl).pathSegment(PoeApiPath.LEAGUE.getStringValue()).toUriString();
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authorizationGrant.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, LeagueBundle.class).getBody();
    }
}
