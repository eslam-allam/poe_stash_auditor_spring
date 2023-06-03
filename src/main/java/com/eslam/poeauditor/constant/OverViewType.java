package com.eslam.poeauditor.constant;

public enum OverViewType implements StringValued {
    CURRENCY("currencyoverview"), ITEM("itemoverview");

    private String type;

    private OverViewType(String type) {
        this.type = type;
    }

    @Override
    public String getStringValue() {
        return this.type;
    }
    
}
