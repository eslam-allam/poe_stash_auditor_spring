package com.eslam.poeauditor.constant;

public enum GrantType implements StringValued{
    AUTHORIZATION_CODE("authorization_code"), CLIENT_CREDENTIALS("client_credentials");

    private final String type;

    private GrantType(String type) {
        this.type = type;
    }
    
    @Override
    public String getStringValue() {
        return type;
    }
}
