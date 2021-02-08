package com.mdgd.pokemon.models.cache

import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Observable

interface Cache {
    fun putSelected(pokemon: PokemonFullDataSchema?)
    fun getSelectedPokemon(): Optional<PokemonFullDataSchema>
    fun getSelectedPokemonObservable(): Observable<Optional<PokemonFullDataSchema>>

    fun addPokemons(list: List<PokemonFullDataSchema>)
    fun getPokemons(): List<PokemonFullDataSchema>
    fun getPokemonsObservable(): Observable<List<PokemonFullDataSchema>>

    fun putLoadingProgress(value: Result<Long>)
    fun getProgressObservable(): Observable<Result<Long>>
}
