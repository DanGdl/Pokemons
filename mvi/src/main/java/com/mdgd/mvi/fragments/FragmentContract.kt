package com.mdgd.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import com.mdgd.mvi.states.ScreenState

class FragmentContract {
    interface ViewModel<V> : LifecycleObserver {
        fun onStateChanged(event: Lifecycle.Event)
        fun getStateObservable(): LiveData<ScreenState<V>>
        fun getEffectObservable(): LiveData<ScreenState<V>>
    }

    interface View
    interface Host
}
