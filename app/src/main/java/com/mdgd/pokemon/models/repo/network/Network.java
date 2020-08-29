package com.mdgd.pokemon.models.repo.network;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface Network {
    Single<Result<List<PokemonDetails>>> loadPokemons();
}
