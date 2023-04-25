package com.mdgd.pokemon.ui.pokemons.adapter;

import com.mdgd.pokemon.adapter.ViewHolderDataItem;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

public class Pokemon implements ViewHolderDataItem {

    public final PokemonFullDataSchema schema;

    public Pokemon(PokemonFullDataSchema schema) {
        this.schema = schema;
    }

    @Override
    public int getViewHolderType(int position) {
        return 0;
    }
}
