package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.Optional;

public enum LeagueRealm implements StringValued {
    PC("pc"), XBOX("xbox"), SONY("sony");

    private String realm;

    private LeagueRealm(String realm) {
        this.realm = realm;
    }
    @Override
    public String getStringValue() {
        return this.realm;
    }
    
    public static LeagueRealm realmSearch(String realm) {
        Optional<LeagueRealm> leagueRealm = Arrays.stream(LeagueRealm.values())
        .filter(rule -> rule.getStringValue().equals(realm)).findAny();

        if (leagueRealm.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid league ID provided: %s", realm));
        }
        return leagueRealm.get();
    }
}
