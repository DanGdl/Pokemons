package com.mdgd.mvi;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

public class FragmentContract {

    public interface ViewModel<T> extends LifecycleObserver {
        LiveData<T> getStateObservable();

        void onAny(LifecycleOwner owner, Lifecycle.Event event);
    }

    public interface View {
    }

    public interface Host {
    }
}
