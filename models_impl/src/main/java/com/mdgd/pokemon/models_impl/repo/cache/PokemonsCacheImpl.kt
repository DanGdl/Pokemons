package com.mdgd.pokemon.models_impl.repo.cache

import com.mdgd.pokemon.models.repo.cache.PokemonsCache
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class PokemonsCacheImpl : PokemonsCache {
    private val pokemons = BehaviorSubject.createDefault<List<PokemonFullDataSchema>>(LinkedList())

    override fun addPokemons(list: List<PokemonFullDataSchema>) {
        val old = pokemons.value
        val pokemonDetails: MutableList<PokemonFullDataSchema> = ArrayList(old.size + list.size)
        pokemonDetails.addAll(old)
        pokemonDetails.addAll(list)
        pokemons.onNext(pokemonDetails)
    }

    override fun getPokemons(): List<PokemonFullDataSchema> {
        return ArrayList(pokemons.value)
    }

    override fun setPokemons(list: List<PokemonFullDataSchema>) {
        pokemons.onNext(ArrayList(list))
    }

    override fun getPokemonsObservable(): Observable<List<PokemonFullDataSchema>> {
        return pokemons
                .map { list: List<PokemonFullDataSchema> -> ArrayList(list) as List<PokemonFullDataSchema> }
                .hide()
    }
}