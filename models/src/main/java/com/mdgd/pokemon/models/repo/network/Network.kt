package com.mdgd.pokemon.models.repo.network

import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface Network {
    fun loadPokemons(bulkSize: Int): Observable<Result<List<PokemonDetails>>>
    fun loadPokemons(page: Int, pageSize: Int): Single<Result<List<PokemonDetails>>>
    fun getPokemonsCount(): Single<Result<Long>>
}
