package com.mdgd.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class FragmentContract {
    interface ViewModel<S, A> : LifecycleObserver {
        fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event)
        fun getStateObservable(): MutableLiveData<S>
        fun getActionObservable(): MutableLiveData<A>
    }

    interface View
    interface Host
}
