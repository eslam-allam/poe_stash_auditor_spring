package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Scope implements StringValued{
    PROFILE("account:profile"), STASHES("account:stashes")
    , LEAGUES("service:leagues"), LEAGES_LADDER("service:leagues:ladder")
    , PVP_MATCHES("service:pvp_matches"), PVP_MATCHES_LADDER("service:pvp_matches:ladder")
    , PUBLIC_STASH_API("service:psapi");

    private final String scopeName;

    private Scope(String scope) {
        this.scopeName = scope;
    }

    @Override
    public String getStringValue() {
        return scopeName;
    }

    public static List<Scope> services() {
        return Arrays.asList(LEAGUES, LEAGES_LADDER, PVP_MATCHES, PVP_MATCHES_LADDER, PUBLIC_STASH_API);
    }
    public static List<Scope> availableServices() {
        return Arrays.asList();
    }

    public static Optional<Scope> getScope(String scope) {
        return Arrays.stream(Scope.class.getEnumConstants()).filter(n -> n.getStringValue().equals(scope)).findAny();
    }
}
