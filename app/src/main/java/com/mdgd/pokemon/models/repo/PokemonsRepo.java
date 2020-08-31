package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface PokemonsRepo {
    int PAGE_SIZE = 30;
    long LOADING_COMPLETE = -1;

    Single<Result<List<PokemonFullDataSchema>>> getPage(Integer page);

    Observable<Result<Long>> loadPokemons();
}
