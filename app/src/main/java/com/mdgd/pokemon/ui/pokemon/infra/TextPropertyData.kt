package com.mdgd.pokemon.ui.pokemon.infra;

public class TextPropertyData implements TextProperty {

    private final String text;
    private final int nestingLevel;

    public TextPropertyData(String text, int nestingLevel) {
        this.text = text;
        this.nestingLevel = nestingLevel;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }

    @Override
    public int getType() {
        return PROPERTY_TEXT;
    }
}
