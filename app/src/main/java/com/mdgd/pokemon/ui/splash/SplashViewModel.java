package com.mdgd.pokemon.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.models.cache.Cache;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class SplashViewModel extends MviViewModel<SplashScreenState> implements SplashContract.ViewModel {

    private final SplashContract.Router router;
    private final Cache cache;

    public SplashViewModel(SplashContract.Router router, Cache cache) {
        this.router = router;
        this.cache = cache;
    }

    @Override
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_START && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    Observable.combineLatest(
                            Observable.timer(1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread()),
                            cache.getProgressObservable()
                                    .observeOn(AndroidSchedulers.mainThread()),
                            (e, result) -> result)
                            .firstOrError()
                            // .map(e -> new Result<Long>(new Throwable("Dummy error"))) // error test
                            .subscribe(value -> {
                                if (value.isError()) {
                                    router.showError(value.getError());
                                } else if (value.getValue() != 0L) {
                                    router.proceedToNextScreen();
                                }
                            }, router::showError));
            router.launchWorker();
        }
    }
}
