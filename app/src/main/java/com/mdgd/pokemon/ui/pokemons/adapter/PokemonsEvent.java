package com.mdgd.pokemon.ui.pokemons.adapter;

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

public class PokemonsEvent {
    public final PokemonFullDataSchema model;

    public PokemonsEvent(PokemonFullDataSchema model) {
        this.model = model;
    }
}
