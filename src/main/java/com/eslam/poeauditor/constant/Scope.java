package com.eslam.poeauditor.constant;

public enum Scope {
    PROFILE("account:profile"), STASHES("account:stashes");

    private final String scopeName;

    private Scope(String scope) {
        this.scopeName = scope;
    }

    public String getScopeName() {
        return scopeName;
    }
}
