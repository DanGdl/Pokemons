package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private final BehaviorSubject<Optional<PokemonFullDataSchema>> pokemon = BehaviorSubject.createDefault(Optional.absent());
    private final BehaviorSubject<List<PokemonFullDataSchema>> pokemons = BehaviorSubject.createDefault(new LinkedList<>());
    private final BehaviorSubject<Result<Long>> progress = BehaviorSubject.createDefault(new Result<>(0L));

    @Override
    public void putSelected(PokemonFullDataSchema pokemon) {
        this.pokemon.onNext(Optional.of(pokemon));
    }

    @Override
    public Optional<PokemonFullDataSchema> getSelectedPokemon() {
        return pokemon.getValue();
    }

    @Override
    public void addPokemons(List<PokemonFullDataSchema> list) {
        final List<PokemonFullDataSchema> old = pokemons.getValue();
        final List<PokemonFullDataSchema> pokemonDetails = new ArrayList<>(old.size() + list.size());
        pokemonDetails.addAll(old);
        pokemonDetails.addAll(list);
        pokemons.onNext(pokemonDetails);
    }

    @Override
    public List<PokemonFullDataSchema> getPokemons() {
        return new ArrayList<>(pokemons.getValue());
    }

    @Override
    public void setPokemons(List<PokemonFullDataSchema> list) {
        pokemons.onNext(new ArrayList<>(list));
    }

    @Override
    public Observable<Optional<PokemonFullDataSchema>> getSelectedPokemonObservable() {
        return pokemon.hide();
    }

    @Override
    public Observable<List<PokemonFullDataSchema>> getPokemonsObservable() {
        return pokemons
                .map(list -> (List<PokemonFullDataSchema>) new ArrayList<>(list))
                .hide();
    }

    @Override
    public void setLoadingProgress(Result<Long> value) {
        progress.onNext(value);
    }

    @Override
    public Observable<Result<Long>> getProgressObservable() {
        return progress;
    }
}
