package com.mdgd.pokemon.ui.pokemon.items;

public class ImageProperty implements PokemonProperty {
    private final String url;

    public ImageProperty(String imageUrl) {
        this.url = imageUrl;
    }

    public String getImageUrl() {
        return url;
    }

    @Override
    public int getViewHolderType(int position) {
        return PROPERTY_IMAGE;
    }
}
