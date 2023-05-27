package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.Optional;

public enum Scope {
    PROFILE("account:profile"), STASHES("account:stashes");

    private final String scopeName;

    private Scope(String scope) {
        this.scopeName = scope;
    }

    public String getScopeName() {
        return scopeName;
    }

    public static Optional<Scope> getScope(String scope) {
        return Arrays.stream(Scope.class.getEnumConstants()).filter(n -> n.getScopeName().equals(scope)).findAny();
    }
}
