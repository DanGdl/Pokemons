package com.mdgd.mvi;

import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mdgd.mvi.fragments.FragmentContract;
import com.mdgd.mvi.states.AbstractState;
import com.mdgd.mvi.states.ScreenState;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class MviViewModel<VIEW, STATE extends AbstractState<VIEW, STATE>> extends ViewModel implements FragmentContract.ViewModel<VIEW> {
    private final CompositeDisposable onStopDisposables = new CompositeDisposable();
    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final MutableLiveData<ScreenState<VIEW>> stateHolder = new MutableLiveData<>();
    private final MutableLiveData<ScreenState<VIEW>> effectHolder = new MutableLiveData<>();

    @Override
    public LiveData<ScreenState<VIEW>> getStateObservable() {
        return stateHolder;
    }

    @Override
    public LiveData<ScreenState<VIEW>> getEffectObservable() {
        return effectHolder;
    }

    @CallSuper
    public void onStateChanged(Lifecycle.Event event) {
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

    protected void setState(STATE state) {
        STATE current = (STATE) stateHolder.getValue();
        if (current != null) {
            state = state.merge(current);
        }
        stateHolder.setValue(state);
    }

    protected STATE getState() {
        return (STATE) stateHolder.getValue();
    }

    protected void setEffect(ScreenState<VIEW> effect) {
        effectHolder.setValue(effect);
    }

    protected boolean hasOnDestroyDisposables() {
        return onDestroyDisposables.size() != 0;
    }

    protected boolean hasOStopDisposables() {
        return onStopDisposables.size() != 0;
    }
}
