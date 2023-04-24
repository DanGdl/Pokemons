package com.mdgd.pokemon.ui.splash;

import androidx.lifecycle.Lifecycle;

import com.mdgd.mvi.MviViewModel;
import com.mdgd.pokemon.models.cache.Cache;
import com.mdgd.pokemon.ui.splash.state.SplashScreenEffect;
import com.mdgd.pokemon.ui.splash.state.SplashScreenState;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class SplashViewModel extends MviViewModel<SplashContract.View, SplashScreenState> implements SplashContract.ViewModel {

    private final Cache cache;

    public SplashViewModel(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void onStateChanged(Lifecycle.Event event) {
        super.onStateChanged(event);
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
                                    setEffect(new SplashScreenEffect.ShowErrorEffect(value.getError()));
                                } else if (value.getValue() != 0L) {
                                    setEffect(new SplashScreenEffect.ProceedToNextScreeEffect());
                                }
                            }, e -> setEffect(new SplashScreenEffect.ShowErrorEffect(e))));
            setEffect(new SplashScreenEffect.LaunchWorkerEffect());
        }
    }
}
