package com.mdgd.mvi;

import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class MviViewModel<T> extends ViewModel implements FragmentContract.ViewModel<T> {
    private final CompositeDisposable onStopDisposables = new CompositeDisposable();
    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final MutableLiveData<T> stateHolder = new MutableLiveData<>();

    @Override
    public LiveData<T> getStateObservable() {
        return stateHolder;
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
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

    protected void postState(T state) {
        stateHolder.postValue(state);
    }

    protected boolean hasOnDestroyDisposables() {
        return onDestroyDisposables.size() != 0;
    }

    protected boolean hasOStopDisposables() {
        return onStopDisposables.size() != 0;
    }

    protected T getLastState() {
        return stateHolder.getValue() == null ? getDefaultState() : stateHolder.getValue();
    }

    protected abstract T getDefaultState();
}
