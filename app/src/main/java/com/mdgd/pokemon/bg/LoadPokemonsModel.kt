package com.mdgd.pokemon.bg

import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import com.mdgd.pokemon.models.repo.PokemonsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoadPokemonsModel(private val repo: PokemonsRepo, private val cache: Cache) : ServiceModel {
    private val disposables = CompositeDisposable()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun load() {
        coroutineScope.launch {
            try {
                val count = repo.loadPokemons_S()
                cache.putLoadingProgress(Result(count))
            } catch (e: Throwable) {
                cache.putLoadingProgress(Result(e))
            }
            cache.putLoadingProgress(Result(PokemonsRepo.LOADING_COMPLETE))
        }

        // todo remove RX
        disposables.addAll( // complete task, dispose all subscriptions
                cache.getProgressObservable()
                        .filter { event: Result<Long> -> event.hasValue() && event.getValue() == -1L }
                        .delay(50, TimeUnit.MILLISECONDS, Schedulers.trampoline())
                        .subscribe {
                            disposables.clear()
                            coroutineScope.cancel()
                        }
        )
    }
}
