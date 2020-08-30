package com.mdgd.pokemon.ui.pokemon.infra;

public class LabelImagePropertyData implements LabelImageProperty {

    private final String labelResId;
    private final String url;
    private final int nestingLevel;

    public LabelImagePropertyData(String label, String imageUrl) {
        this(label, imageUrl, 0);
    }

    public LabelImagePropertyData(String name, String url, int nestingLevel) {
        this.labelResId = name;
        this.url = url;
        this.nestingLevel = nestingLevel;
    }

    @Override
    public String getImageUrl() {
        return url;
    }

    @Override
    public int getType() {
        return PROPERTY_LABEL_IMAGE;
    }

    @Override
    public int getNestingLevel() {
        return nestingLevel;
    }

    @Override
    public String getLabel() {
        return labelResId;
    }
}
