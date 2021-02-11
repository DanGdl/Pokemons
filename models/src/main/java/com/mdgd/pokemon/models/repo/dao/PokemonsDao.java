package com.mdgd.pokemon.models.repo.dao;

import com.google.common.base.Optional;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.network.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface PokemonsDao {

    Completable save(List<PokemonDetails> list);

    Single<Result<List<PokemonFullDataSchema>>> getPage(int page, int pageSize);

    long getCount();

    Observable<Optional<PokemonFullDataSchema>> getPokemonById(Long id);
}
