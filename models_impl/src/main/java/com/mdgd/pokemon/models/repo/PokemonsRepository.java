package com.mdgd.pokemon.models.repo;

import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.cache.PokemonsCache;
import com.mdgd.pokemon.models.repo.dao.PokemonsDao;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;
import com.mdgd.pokemon.models.repo.network.Network;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PokemonsRepository implements PokemonsRepo {
    private final PokemonsDao dao;
    private final Network network;
    private final PokemonsCache cache;

    public PokemonsRepository(PokemonsDao pokemonsDao, Network pokemonsNetwork, PokemonsCache cache) {
        this.dao = pokemonsDao;
        this.network = pokemonsNetwork;
        this.cache = cache;
    }

    @Override
    public Single<Result<List<PokemonFullDataSchema>>> getPage(Integer page) {
        return dao.getPage(page, PAGE_SIZE)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Single.just(new Result<>(result.getError()));
                    } else {
                        final List<PokemonFullDataSchema> list = result.getValue();
                        return list.isEmpty() ? loadPage(page) : Single.just(new Result<>(list));
                    }
                });
    }

    @Override
    public Observable<Result<Long>> loadPokemons() {
        final long count = dao.getCount();
        return network.getPokemonsCount()
                .flatMapObservable(result -> {
                    if (result.isError()) {
                        return Observable.just(new Result<>(result.getError()));
                    } else if (count == result.getValue()) {
                        return Observable.just(new Result<>(count));
                    } else {
                        return loadPokemonsInner();
                    }
                });

    }

    @Override
    public List<PokemonFullDataSchema> getPokemons() {
        return cache.getPokemons();
    }

    private Observable<Result<Long>> loadPokemonsInner() {
        return network.loadPokemons(PAGE_SIZE * 3)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Observable.just(new Result<>(result.getError()));
                    } else {
                        return dao.save(result.getValue())
                                .toSingleDefault(new Result<>((long) result.getValue().size()))
                                .toObservable();
                    }
                });
    }

    private Single<Result<List<PokemonFullDataSchema>>> loadPage(Integer page) {
        return network.loadPokemons(page, PAGE_SIZE)
                .flatMap(result -> {
                    if (result.isError()) {
                        return Single.just(new Result<>(result.getError()));
                    } else {
                        return dao.save(result.getValue())
                                .andThen(dao.getPage(page, PAGE_SIZE))
                                .doOnEvent((e, error) -> {
                                    if (!result.isError()) {
                                        if (page == 0) {
                                            cache.setPokemons(e.getValue());
                                        } else {
                                            cache.addPokemons(e.getValue());
                                        }
                                    }
                                });
                    }
                });
    }
}
