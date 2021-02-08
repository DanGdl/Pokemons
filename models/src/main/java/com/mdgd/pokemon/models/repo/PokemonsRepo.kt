package com.mdgd.pokemon.models.repo

import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PokemonsRepo {
    fun getPage(page: Int): Single<Result<List<PokemonFullDataSchema>>>
    fun loadPokemons(): Observable<Result<Long>>

    companion object {
        const val PAGE_SIZE = 30
        const val LOADING_COMPLETE: Long = -1
    }
}
