package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CacheImpl implements Cache {
    private BehaviorSubject<Optional<PokemonDetails>> pokemon = BehaviorSubject.createDefault(Optional.absent());
    private List<PokemonDetails> pokemons = new LinkedList<>();

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
        pokemons.addAll(list);
    }

    @Override
    public List<PokemonDetails> getPokemons() {
        return new ArrayList<>(pokemons);
    }

    @Override
    public void setPokemons(List<PokemonDetails> list) {
        pokemons.clear();
        pokemons.addAll(list);
    }
}
