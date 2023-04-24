package com.mdgd.pokemon.models.filters;

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

public interface CharacteristicComparator {
    int compare(PokemonFullDataSchema p1, PokemonFullDataSchema p2);
}
