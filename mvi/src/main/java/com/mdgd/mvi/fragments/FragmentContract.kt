package com.mdgd.mvi.fragments

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData

class FragmentContract {
    interface ViewModel<S, A> : LifecycleObserver {
        fun getStateObservable(): MutableLiveData<S>
        fun getActionObservable(): MutableLiveData<A>
    }

    interface View
    interface Host
}
