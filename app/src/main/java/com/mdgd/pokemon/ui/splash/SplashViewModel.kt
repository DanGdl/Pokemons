package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mdgd.mvi.MviViewModel
import com.mdgd.pokemon.models.cache.Cache
import com.mdgd.pokemon.models.infra.Result
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel(private val router: SplashContract.Router, private val cache: Cache) : MviViewModel<SplashScreenState>(), SplashContract.ViewModel {

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        super.onAny(owner, event)
        if (event == Lifecycle.Event.ON_START && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    Observable.combineLatest(
                            Observable.timer(1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread()),

                            cache.getProgressObservable()
                                    .skip(1) // skip default value
                                    .observeOn(AndroidSchedulers.mainThread()),

                            { _: Long, result: Result<Long> -> result })
                            .firstOrError()
                            // .map(e -> new Result<Long>(new Throwable("Dummy error"))) // error test
                            .subscribe({ value: Result<Long> ->
                                if (value.isError()) {
                                    router.showError(value.getError())
                                } else if (value.getValue() != 0L) {
                                    router.proceedToNextScreen()
                                }
                            }, { error: Throwable? -> router.showError(error) }))
            router.launchWorker()
        }
    }
}
