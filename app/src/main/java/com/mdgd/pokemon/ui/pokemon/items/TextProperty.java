package com.mdgd.pokemon.ui.pokemon.items;

public class TextProperty implements PokemonProperty {

    private final String text;
    private final int nestingLevel;

    public TextProperty(String text, int nestingLevel) {
        this.text = text;
        this.nestingLevel = nestingLevel;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }

    @Override
    public int getViewHolderType(int position) {
        return PROPERTY_TEXT;
    }
}
