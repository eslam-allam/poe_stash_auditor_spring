package com.eslam.poeauditor.constant;

import java.util.Arrays;
import java.util.List;

public enum ItemCategory implements StringValued{

    CURRENCY("Currency", OverViewType.CURRENCY), FRAGMENT("Fragment", OverViewType.CURRENCY), 
    OIL("Oil", OverViewType.ITEM), INCUBATOR("Incubator", OverViewType.ITEM), 
    SCARAB("Scarab", OverViewType.ITEM), FOSSIL("Fossil", OverViewType.ITEM), 
    RESONATOR("Resonator", OverViewType.ITEM), ESSENCE("Essence", OverViewType.ITEM), 
    DIVINATION_CARD("DivinationCard", OverViewType.ITEM), 
    SKILL_GEM("SkillGem", OverViewType.ITEM), UNIQUE_MAP("UniqueMap", OverViewType.ITEM), 
    UNIQUE_JEWEL("UniqueJewel", OverViewType.ITEM), UNIQUE_FLASK("UniqueFlask", OverViewType.ITEM), 
    UNIQUE_WEAPON("UniqueWeapon", OverViewType.ITEM), UNIQUE_ARMOUR("UniqueArmour", OverViewType.ITEM), 
    UNIQUE_ACCESSORY("UniqueAccessory", OverViewType.ITEM), BEAST("Beast", OverViewType.ITEM);

    private String category;
    private OverViewType overViewType;

    private ItemCategory(String category, OverViewType overViewType) {
        this.category = category;
        this.overViewType = overViewType;
    }

    @Override
    public String getStringValue() {
        return this.category;
    }

    public OverViewType getOverViewType() {
        return overViewType;
    }

    public static List<ItemCategory> getCategories() {
        return Arrays.asList(ItemCategory.class.getEnumConstants());
    }
    
}
