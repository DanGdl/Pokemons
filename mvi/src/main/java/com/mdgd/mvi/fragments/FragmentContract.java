package com.mdgd.mvi.fragments;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;

import com.mdgd.mvi.states.ScreenState;

public class FragmentContract {

    public interface ViewModel<V> extends LifecycleObserver {
        LiveData<ScreenState<V>> getStateObservable();

        LiveData<ScreenState<V>> getEffectObservable();

        void onStateChanged(Lifecycle.Event event);
    }

    public interface View {
    }

    public interface Host {
    }
}
