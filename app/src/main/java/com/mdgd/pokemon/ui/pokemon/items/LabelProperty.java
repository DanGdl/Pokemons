package com.mdgd.pokemon.ui.pokemon.items;

public class LabelProperty extends TitleProperty {

    private final String text;
    private final String textStr;

    public LabelProperty(int labelResId, String text) {
        super(labelResId, 0);
        this.text = text;
        this.textStr = "";
    }

    public LabelProperty(String name, String text, int nestingLevel) {
        super(0, nestingLevel);
        this.textStr = name;
        this.text = text;
    }

    @Override
    public int getViewHolderType(int position) {
        return PROPERTY_LABEL;
    }

    public String getText() {
        return text;
    }

    public String getTitleStr() {
        return textStr;
    }
}
