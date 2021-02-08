package com.mdgd.pokemon.models.cache

import com.google.common.base.Optional
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class CacheImpl : Cache {
    private val pokemon = BehaviorSubject.createDefault(Optional.absent<PokemonFullDataSchema>())
    private val pokemons = BehaviorSubject.createDefault<List<PokemonFullDataSchema>>(LinkedList())
    private val progress = BehaviorSubject.createDefault(Result(0L))

    override fun putSelected(pokemon: PokemonFullDataSchema?) {
        this.pokemon.onNext(Optional.of(pokemon))
    }

    override fun getSelectedPokemon(): Optional<PokemonFullDataSchema> {
        return pokemon.value
    }

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

    fun setPokemons(list: List<PokemonFullDataSchema>?) {
        pokemons.onNext(ArrayList(list))
    }

    override fun getSelectedPokemonObservable(): Observable<Optional<PokemonFullDataSchema>> {
        return pokemon.hide()
    }

    override fun getPokemonsObservable(): Observable<List<PokemonFullDataSchema>> {
        return pokemons
                .map { list: List<PokemonFullDataSchema> -> ArrayList(list) as List<PokemonFullDataSchema> }
                .hide()
    }

    override fun putLoadingProgress(value: Result<Long>) {
        progress.onNext(value)
    }

    override fun getProgressObservable(): Observable<Result<Long>> {
        return progress
    }
}
