package com.mdgd.pokemon.models.repo.dao;

import com.mdgd.pokemon.models.repo.schemas.PokemonDetails;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface PokemonsDao {

    Completable save(List<PokemonDetails> list);

    Single<List<PokemonDetails>> getPage(int page, int pageSize);
}
