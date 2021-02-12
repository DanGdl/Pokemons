package com.mdgd.pokemon.models.repo.dao

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails

interface PokemonsDao {
    companion object {
        const val NO_MORE_POKEMONS_MSG = "No more pokemons in DAO"
    }

    suspend fun save(list: List<PokemonDetails>)
    suspend fun getPage(page: Int, pageSize: Int): List<PokemonFullDataSchema>
    suspend fun getCount(): Long
    suspend fun getPokemonById(pokemonId: Long): PokemonFullDataSchema?
}
