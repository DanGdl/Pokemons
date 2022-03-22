package com.mdgd.mvi.fragments

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData

class FragmentContract {
    interface ViewModel<S, E> {
        fun onStateChanged(event: Lifecycle.Event)
        fun getStateObservable(): LiveData<S>
        fun getActionObservable(): LiveData<E>
    }

    interface View
    interface Host
}
