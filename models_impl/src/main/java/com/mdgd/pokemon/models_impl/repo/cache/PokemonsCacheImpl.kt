package com.mdgd.pokemon.models_impl.repo.cache

import com.mdgd.pokemon.models.repo.cache.PokemonsCache
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

class PokemonsCacheImpl : PokemonsCache {
    private val pokemons = mutableListOf<PokemonFullDataSchema>()

    override fun addPokemons(list: List<PokemonFullDataSchema>) {
        pokemons.addAll(list)
    }

    override fun getPokemons(): List<PokemonFullDataSchema> = ArrayList(pokemons)

    override fun setPokemons(list: List<PokemonFullDataSchema>) {
        pokemons.clear()
        pokemons.addAll(list)
    }
}
