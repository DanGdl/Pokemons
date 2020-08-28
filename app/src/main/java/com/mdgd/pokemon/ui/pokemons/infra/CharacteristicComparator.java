package com.mdgd.pokemon.ui.pokemons.infra;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

public interface CharacteristicComparator {
    int compare(PokemonDetails p1, PokemonDetails p2);
}
