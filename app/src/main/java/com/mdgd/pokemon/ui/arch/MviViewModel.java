package com.mdgd.pokemon.ui.arch;

import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MviViewModel<T> extends ViewModel implements FragmentContract.ViewModel<T> {
    private final CompositeDisposable onStopDisposables = new CompositeDisposable();
    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final MutableLiveData<T> stateHolder = new MutableLiveData<>();

    public MutableLiveData<T> getStateObservable() {
        return stateHolder;
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_STOP) {
            onStopDisposables.clear();
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroyDisposables.clear();
        }
    }

    protected void observeTillStop(Disposable... subscriptions) {
        onStopDisposables.addAll(subscriptions);
    }

    protected void observeTillDestroy(Disposable... subscriptions) {
        onDestroyDisposables.addAll(subscriptions);
    }

    protected void setState(T state) {
        stateHolder.setValue(state);
    }

    protected boolean hasOnDestroyDisposables() {
        return onDestroyDisposables.size() != 0;
    }
}
