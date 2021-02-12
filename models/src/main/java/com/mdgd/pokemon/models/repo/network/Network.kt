package com.mdgd.pokemon.models.repo.network

import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails

interface Network {
    suspend fun loadPokemons(pokemonsCount: Long, offset: Long): List<PokemonDetails>
    suspend fun loadPokemons(page: Int, pageSize: Int): List<PokemonDetails>
    suspend fun getPokemonsCount(): Long
}
