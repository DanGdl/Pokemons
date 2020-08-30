package com.mdgd.pokemon.ui.pokemon.infra;

public class LabelPropertyData extends TitlePropertyData implements LabelProperty {

    private final String text;

    public LabelPropertyData(int labelResId, String text) {
        super(labelResId, 0);
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
}
