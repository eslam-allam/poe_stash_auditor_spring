package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum StashType implements StringValued{
    MAP("MapStash"), QUAD("QuadStash"), FRAGMENT("FragmentStash"), CURRENCY("CurrencyStash"), PREMIUM("PremiumStash"),
    ESSENCE("EssenceStash"), DIVINATION("DivinationCardStash"), NORMAL("NormalStash");

    private String type;

    private StashType(String type) {
        this.type = type;
    }

    public static StashType typeSearch(String type) {
        Optional<StashType> stashType = Arrays.asList(StashType.values()).stream().filter(stash -> stash.getStringValue().
        equals(type)).findAny();

        if (stashType.isPresent()) {
            return stashType.get();
        }
        return NORMAL;
    }

    public static List<StashType> implementedStashes() {
        return Arrays.asList(StashType.values()).stream().filter(s -> !s.equals(MAP)).collect(Collectors.toList());
    }

    @Override
    public String getStringValue() {
        return this.type;
    }

    
}
