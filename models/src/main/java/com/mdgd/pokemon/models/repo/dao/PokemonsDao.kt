package com.mdgd.pokemon.models.repo.dao

import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PokemonsDao {
    fun save(list: List<PokemonDetails>): Completable
    fun getPage(page: Int, pageSize: Int): Single<Result<List<PokemonFullDataSchema>>>
    fun getCount(): Long
    fun getPokemonById(pokemonId: Long): Observable<Optional<PokemonFullDataSchema>>
}
