package com.mdgd.pokemon.models.repo.cache;

import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface PokemonsCache {

    void addPokemons(List<PokemonFullDataSchema> list);

    List<PokemonFullDataSchema> getPokemons();

    void setPokemons(List<PokemonFullDataSchema> list);

    Observable<List<PokemonFullDataSchema>> getPokemonsObservable();
}
