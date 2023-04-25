package com.mdgd.pokemon.ui.pokemon.items;

import com.mdgd.pokemon.adapter.ViewHolderDataItem;

public interface PokemonProperty extends ViewHolderDataItem {
    int PROPERTY_IMAGE = 1;
    int PROPERTY_LABEL = 2;
    int PROPERTY_TITLE = 3;
    int PROPERTY_TEXT = 4;

    default int getNestingLevel() {
        return 0;
    }
}
