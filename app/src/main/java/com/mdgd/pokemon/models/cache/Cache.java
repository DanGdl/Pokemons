package com.mdgd.pokemon.models.cache;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public interface Cache {
    void putSelected(PokemonFullDataSchema pokemon);

    @Nullable Optional<PokemonFullDataSchema> getSelectedPokemon();

    Observable<Optional<PokemonFullDataSchema>> getSelectedPokemonObservable();

    void addPokemons(List<PokemonFullDataSchema> list);

    List<PokemonFullDataSchema> getPokemons();

    void setPokemons(List<PokemonFullDataSchema> list);

    Observable<List<PokemonFullDataSchema>> getPokemonsObservable();
}
