package com.mdgd.pokemon.models.repo.dao;

import com.mdgd.pokemon.dto.PokemonDetails;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class PokemonsDaoImpl implements PokemonsDao {

    @Override
    public Completable save(List<PokemonDetails> list) {
        return Completable.complete();
    }

    @Override
    public Single<List<PokemonDetails>> getPage(int page, int pageSize) {
        return Single.just(new ArrayList<>());
    }
}
