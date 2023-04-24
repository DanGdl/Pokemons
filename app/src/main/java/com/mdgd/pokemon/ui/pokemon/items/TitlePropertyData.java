package com.mdgd.pokemon.ui.pokemon.items;

public class TitlePropertyData implements TitleProperty {

    private final int titleResId;
    private final int nestingLevel;

    public TitlePropertyData(int titleResId) {
        this(titleResId, 0);
    }

    public TitlePropertyData(int titleResId, int nestingLevel) {
        this.titleResId = titleResId;
        this.nestingLevel = nestingLevel;
    }

    @Override
    public int getTitleResId() {
        return titleResId;
    }

    @Override
    public int getType() {
        return PROPERTY_TITLE;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }
}
