package com.mdgd.pokemon.models.repo.cache

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

interface PokemonsCache {

    fun addPokemons(list: List<PokemonFullDataSchema>)
    fun setPokemons(list: List<PokemonFullDataSchema>)
    fun getPokemons(): List<PokemonFullDataSchema>
}