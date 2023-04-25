package com.mdgd.pokemon.ui.pokemon.items;

public class TitleProperty implements PokemonProperty {

    private final int titleResId;
    private final int nestingLevel;

    public TitleProperty(int titleResId) {
        this(titleResId, 0);
    }

    public TitleProperty(int titleResId, int nestingLevel) {
        this.titleResId = titleResId;
        this.nestingLevel = nestingLevel;
    }

    public int getTitleResId() {
        return titleResId;
    }

    @Override
    public int getViewHolderType(int position) {
        return PROPERTY_TITLE;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }
}
