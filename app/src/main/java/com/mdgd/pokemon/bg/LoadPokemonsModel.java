package com.mdgd.pokemon.bg;

import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.models.infra.Result;
import com.mdgd.pokemon.models.repo.PokemonsRepo;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoadPokemonsModel implements LoadPokemonsContract.ServiceModel {

    private CompositeDisposable disposables = new CompositeDisposable();
    private final PokemonsRepo repo;
    private final Cache cache;

    public LoadPokemonsModel(PokemonsRepo repo, Cache cache) {
        this.repo = repo;
        this.cache = cache;
    }

    @Override
    public void load() {
        disposables.addAll(
                // complete task, dispose all subscriptions
                cache.getProgressObservable()
                        .filter(event -> event.getValue() == -1)
                        .delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                        .subscribe(event -> disposables.clear()),

                repo.loadPokemons()
                        .doFinally(() -> cache.putLoadingProgress(new Result<>(PokemonsRepo.LOADING_COMPLETE)))
                        .subscribe(cache::putLoadingProgress, error -> cache.putLoadingProgress(new Result<>(error)))
        );
    }
}
