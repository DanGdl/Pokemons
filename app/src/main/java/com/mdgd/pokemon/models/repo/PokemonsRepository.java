package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.network.Network;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class PokemonsRepository implements PokemonsRepo {
    private final PokemonsDao dao;
    private final Network network;

    public PokemonsRepository(PokemonsDao pokemonsDao, Network pokemonsNetwork) {
        this.dao = pokemonsDao;
        this.network = pokemonsNetwork;
    }

    // maybe move loading to splash
    @Override
    public Single<Result<List<PokemonFullDataSchema>>> getPage(Integer page) {
        return dao.getPage(page, PAGE_SIZE)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Single.just(new Result<>(result.getError()));
                    } else {
                        final List<PokemonFullDataSchema> list = result.getValue();
                        return list.isEmpty() ? loadPokemons(page) : Single.just(new Result<>(list));
                    }
                });
    }

    private Single<Result<List<PokemonFullDataSchema>>> loadPokemons(Integer page) {
        return network.loadPokemons()
                .flatMap(result -> {
                    if (result.isError()) {
                        return Single.just(new Result<>(result.getError()));
                    } else {
                        return dao.save(result.getValue())
                                .andThen(dao.getPage(page, PAGE_SIZE));
                    }
                });
    }
}
