package com.mdgd.pokemon.models.repo

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

interface PokemonsRepo {
    fun getPokemons(): List<PokemonFullDataSchema>

    suspend fun getPage(page: Int): List<PokemonFullDataSchema>
    suspend fun loadPokemons(initialAmount: Long): Long
    suspend fun getPokemonById(pokemonId: Long): PokemonFullDataSchema?
    suspend fun loadInitialPages(amount: Long)

    companion object {
        const val PAGE_SIZE = 30
    }
}
