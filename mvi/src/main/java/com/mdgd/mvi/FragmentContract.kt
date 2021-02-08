package com.mdgd.mvi

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData

class FragmentContract {
    interface ViewModel<T> : LifecycleObserver {
        fun getStateObservable(): MutableLiveData<T>
    }

    interface View
    interface Host
}
