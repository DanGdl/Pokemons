package com.mdgd.pokemon.bg

import com.mdgd.pokemon.bg.LoadPokemonsContract.ServiceModel
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoadPokemonsModel(private val repo: PokemonsRepo, private val cache: Cache) : ServiceModel {
    private val disposables = CompositeDisposable()

    override fun load() {
        disposables.addAll( // complete task, dispose all subscriptions
                cache.getProgressObservable()
                        .filter { event: Result<Long> -> event.hasValue() && event.getValue() == -1L }
                        .delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                        .subscribe { disposables.clear() },

                repo.loadPokemons()
                        .doFinally { cache.putLoadingProgress(Result(PokemonsRepo.LOADING_COMPLETE)) }
                        .subscribe({ value: Result<Long> -> cache.putLoadingProgress(value) }, { error: Throwable -> cache.putLoadingProgress(Result(error)) })
        )
    }
}
