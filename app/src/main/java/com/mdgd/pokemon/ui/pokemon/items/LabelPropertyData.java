package com.mdgd.pokemon.ui.pokemon.items;

public class LabelPropertyData extends TitlePropertyData implements LabelProperty {

    private final String text;
    private final String textStr;

    public LabelPropertyData(int labelResId, String text) {
        super(labelResId, 0);
        this.text = text;
        this.textStr = "";
    }

    public LabelPropertyData(String name, String text, int nestingLevel) {
        super(0, nestingLevel);
        this.textStr = name;
        this.text = text;
    }

    @Override
    public int getType() {
        return PROPERTY_LABEL;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTitleStr() {
        return textStr;
    }
}
