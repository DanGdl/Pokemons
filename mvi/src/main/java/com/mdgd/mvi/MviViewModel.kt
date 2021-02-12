package com.mdgd.mvi

import androidx.lifecycle.*

open class MviViewModel<T> : ViewModel(), FragmentContract.ViewModel<T> {
    private val stateHolder = MutableLiveData<T>() // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?

    override fun getStateObservable(): MutableLiveData<T> {
        return stateHolder
    }

    protected fun setState(state: T) {
        stateHolder.value = state
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
    }
}
