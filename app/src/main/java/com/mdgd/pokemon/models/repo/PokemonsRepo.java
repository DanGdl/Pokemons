package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface PokemonsRepo {
    int PAGE_SIZE = 30;

    Single<Result<List<PokemonDetails>>> getPage(Integer page);
}
