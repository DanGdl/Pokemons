package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public interface Cache {
    void putSelected(PokemonDetails pokemon);

    @Nullable Optional<PokemonDetails> getSelectedPokemon();

    Observable<Optional<PokemonDetails>> getSelectedPokemonObservable();

    void setPokemons(List<PokemonDetails> list);

    void addPokemons(List<PokemonDetails> list);

    List<PokemonDetails> getPokemons();

    Observable<List<PokemonDetails>> getPokemonsObservable();
}
