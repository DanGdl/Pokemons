package com.mdgd.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

class FragmentContract {
    interface ViewModel<V, S, E> : LifecycleObserver {
        fun onStateChanged(event: Lifecycle.Event)
        fun getStateObservable(): LiveData<S>
        fun getEffectObservable(): LiveData<E>
    }

    interface View
    interface Host
}
