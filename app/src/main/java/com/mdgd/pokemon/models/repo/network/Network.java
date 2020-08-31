package com.mdgd.pokemon.models.repo.network;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface Network {
    Observable<Result<List<PokemonDetails>>> loadPokemons(int bulkSize);

    Single<Result<List<PokemonDetails>>> loadPokemons(Integer page, int pageSize);

    Single<Result<Long>> getPokemonsCount();
}
