package com.mdgd.pokemon.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.models.cache.Cache;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class SplashViewModel extends MviViewModel<SplashScreenState> implements SplashContract.ViewModel {

    private final Cache cache;

    public SplashViewModel(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_START && !hasOnDestroyDisposables()) {
            observeTillDestroy(
                    Observable.combineLatest(
                            Observable.timer(1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread()),
                            cache.getProgressObservable()
                                    .skip(1) // skip default value
                                    .observeOn(AndroidSchedulers.mainThread()),
                            (e, result) -> result)
                            .firstOrError()
                            // .map(e -> new Result<Long>(new Throwable("Dummy error"))) // error test
                            .subscribe(value -> {
                                if (value.isError()) {
                                    setState(SplashScreenState.createShowError(value.getError()));
                                } else if (value.getValue() != 0L) {
                                    setState(SplashScreenState.createNextScreen());
                                }
                            }, e -> setState(SplashScreenState.createShowError(e))));
            setState(SplashScreenState.createLaunchWorkerState());
        }
    }

    @Override
    protected SplashScreenState getDefaultState() {
        return SplashScreenState.createDefaultState();
    }
}
