package com.mdgd.pokemon.models.repo

import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Single

interface PokemonsRepo {
    fun getPage(page: Int): Single<Result<List<PokemonFullDataSchema>>>
    fun getPokemons(): List<PokemonFullDataSchema>


    suspend fun getPage_S(page: Int): List<PokemonFullDataSchema>
    suspend fun loadPokemons_S(initialAmount: Long): Long
    suspend fun getPokemonById_S(pokemonId: Long): Optional<PokemonFullDataSchema>
    suspend fun loadInitialPages(amount: Long)

    companion object {
        const val PAGE_SIZE = 30
    }
}
