package com.mdgd.pokemon.models.filters

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

interface CharacteristicComparator {
    fun compare(p1: PokemonFullDataSchema, p2: PokemonFullDataSchema): Int
}
