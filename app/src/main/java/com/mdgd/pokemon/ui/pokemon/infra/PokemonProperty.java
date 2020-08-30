package com.mdgd.pokemon.ui.pokemon.infra;

public interface PokemonProperty {
    int PROPERTY_IMAGE = 1;
    int PROPERTY_LABEL = 2;
    int PROPERTY_TITLE = 3;
    int PROPERTY_LABEL_IMAGE = 4;

    int getType();

    default int getNestingLevel() {
        return 0;
    }
}
