package com.mdgd.pokemon.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.mdgd.pokemon.ui.arch.MviViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashViewModel extends MviViewModel<SplashScreenState> implements SplashContract.ViewModel {

    private final SplashContract.Router router;

    public SplashViewModel(SplashContract.Router router) {
        this.router = router;
    }

    @Override
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        super.onAny(owner, event);
        if (event == Lifecycle.Event.ON_START) {
            observeTillStop(Single.timer(500, TimeUnit.MILLISECONDS, Schedulers.computation())
                    .subscribe(val -> router.proceedToNextScreen()));
        }
    }
}
