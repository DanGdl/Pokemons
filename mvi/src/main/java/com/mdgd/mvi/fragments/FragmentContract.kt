package com.mdgd.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.mdgd.mvi.states.ScreenEffect

class FragmentContract {
    interface ViewModel<V, S> : LifecycleObserver {
        fun onStateChanged(event: Lifecycle.Event)
        fun getStateObservable(): LiveData<S>
        fun getEffectObservable(): LiveData<ScreenEffect<V>>
    }

    interface View
    interface Host
}
