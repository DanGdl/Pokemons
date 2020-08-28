package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private BehaviorSubject<Optional<PokemonDetails>> pokemon = BehaviorSubject.createDefault(Optional.absent());
    private BehaviorSubject<List<PokemonDetails>> pokemons = BehaviorSubject.createDefault(new LinkedList<>());

    @Override
    public void putSelected(PokemonDetails pokemon) {
        this.pokemon.onNext(Optional.of(pokemon));
    }

    @Override
    public Optional<PokemonDetails> getSelectedPokemon() {
        return pokemon.getValue();
    }

    @Override
    public void addPokemons(List<PokemonDetails> list) {
        final List<PokemonDetails> old = pokemons.getValue();
        final List<PokemonDetails> pokemonDetails = new ArrayList<>(old.size() + list.size());
        pokemonDetails.addAll(old);
        pokemonDetails.addAll(list);
        pokemons.onNext(pokemonDetails);
    }

    @Override
    public List<PokemonDetails> getPokemons() {
        return new ArrayList<>(pokemons.getValue());
    }

    @Override
    public void setPokemons(List<PokemonDetails> list) {
        pokemons.onNext(new ArrayList<>(list));
    }

    @Override
    public Observable<Optional<PokemonDetails>> getSelectedPokemonObservable() {
        return pokemon.hide();
    }

    @Override
    public Observable<List<PokemonDetails>> getPokemonsObservable() {
        return pokemons
                .map(list -> (List<PokemonDetails>) new ArrayList<>(list))
                .hide();
    }
}
