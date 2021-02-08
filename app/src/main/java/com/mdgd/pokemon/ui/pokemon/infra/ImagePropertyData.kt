package com.mdgd.pokemon.ui.pokemon.infra;

public class ImagePropertyData implements ImageProperty {
    private final String url;

    public ImagePropertyData(String imageUrl) {
        this.url = imageUrl;
    }

    @Override
    public String getImageUrl() {
        return url;
    }

    @Override
    public int getType() {
        return PROPERTY_IMAGE;
    }
}
