package com.mdgd.mvi

import androidx.lifecycle.*

abstract class MviViewModel<T> : ViewModel(), FragmentContract.ViewModel<T> {
    private val stateHolder = MutableLiveData<T>() // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?

    override fun getStateObservable(): MutableLiveData<T> {
        return stateHolder
    }

    protected fun setState(state: T) {
        stateHolder.value = state
    }

    protected fun getLastState(): T {
        return if (stateHolder.value == null) getDefaultState() else stateHolder.value!!
    }

    protected abstract fun getDefaultState(): T

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
    }
}
