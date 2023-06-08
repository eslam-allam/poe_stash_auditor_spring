package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.Optional;

public enum LeagueRuleType implements StringValued{
    HARDCORE("Hardcore", true), 
    SOLO_SELF_FOUND("NoParties", false), 
    RUTHLESS("HardMode", false);

    private String ruleName;
    private Boolean enabled;

    private LeagueRuleType(String ruleName, Boolean enabled) {
        this.ruleName = ruleName;
        this.enabled = enabled;
    }
    @Override
    public String getStringValue() {
        return this.ruleName;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public static LeagueRuleType leagueSearch(String leagueId) {
        Optional<LeagueRuleType> leagueRule = Arrays.stream(LeagueRuleType.values())
        .filter(rule -> rule.getStringValue().equals(leagueId)).findAny();

        if (leagueRule.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid league ID provided: %s", leagueId));
        }
        return leagueRule.get();
    }


}
