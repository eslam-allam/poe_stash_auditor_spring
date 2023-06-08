package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Scope implements StringValued{
    PROFILE("account:profile", ScopeType.ACCOUNT, true), 
    STASHES("account:stashes", ScopeType.ACCOUNT, true),
    LEAGUES("service:leagues", ScopeType.SERVICE, true), 
    LEAGES_LADDER("service:leagues:ladder", ScopeType.SERVICE, false), 
    PVP_MATCHES("service:pvp_matches", ScopeType.SERVICE, false), 
    PVP_MATCHES_LADDER("service:pvp_matches:ladder", ScopeType.SERVICE, false), 
    PUBLIC_STASH_API("service:psapi", ScopeType.SERVICE, false);

    private final String scopeName;
    private final ScopeType scopeType;
    private final Boolean available;

    private Scope(String scope, ScopeType scopeType, Boolean available) {
        this.scopeName = scope;
        this.scopeType = scopeType;
        this.available = available;
    }

    @Override
    public String getStringValue() {
        return scopeName;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public Boolean isAvailable() {
        return available;
    }

    public static List<Scope> services() {
        return Arrays.stream(Scope.values()).filter(s -> s.getScopeType().equals(ScopeType.SERVICE)).toList();
    }
    public static List<Scope> availableServices() {
        return Arrays.stream(Scope.values()).filter(s -> s.getScopeType().equals(ScopeType.SERVICE)
         && s.isAvailable()).toList();
    }

    public static Optional<Scope> getScope(String scope) {
        return Arrays.stream(Scope.class.getEnumConstants()).filter(n -> n.getStringValue().equals(scope)).findAny();
    }
}
