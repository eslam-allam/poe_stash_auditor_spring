package com.eslam.poeauditor.constant;

public enum GrantType {
    AUTHORIZATION_CODE("authorization_code"), CLIENT_CREDENTIALS("client_credentials");

    private final String type;

    private GrantType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
