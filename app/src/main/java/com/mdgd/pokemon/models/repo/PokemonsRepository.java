package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.dto.PokemonDetails;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
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

    @Override
    public Single<Result<List<PokemonDetails>>> getPage(Integer page) {
        return dao.getPage(page, PAGE_SIZE)
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        return loadPage(page);
                        // todo save
                    } else {
                        return Single.just(new Result<>(list));
                    }
                });
    }

    private Single<Result<List<PokemonDetails>>> loadPage(Integer page) {
        return network.loadPokemonsPage(page, PAGE_SIZE);
    }
}
