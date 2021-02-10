package com.mdgd.pokemon.models.repo.cache

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Observable

interface PokemonsCache {

    fun addPokemons(list: List<PokemonFullDataSchema>)
    fun setPokemons(list: List<PokemonFullDataSchema>)
    fun getPokemons(): List<PokemonFullDataSchema>
    fun getPokemonsObservable(): Observable<List<PokemonFullDataSchema>>
}