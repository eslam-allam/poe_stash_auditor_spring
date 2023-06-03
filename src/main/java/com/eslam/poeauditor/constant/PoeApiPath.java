package com.eslam.poeauditor.constant;

public enum PoeApiPath implements StringValued{
    STASH("stash"), LEAGUE("league");

    private String path;

    private PoeApiPath(String path) {
        this.path = path;
    }

    @Override
    public String getStringValue() {
        return this.path;
    }
    
}
