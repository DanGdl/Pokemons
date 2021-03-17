package com.mdgd.mvi

import androidx.lifecycle.*
import com.mdgd.mvi.fragments.FragmentContract

abstract class MviViewModel<S, A> : ViewModel(), FragmentContract.ViewModel<S, A> {
    private val stateHolder = MutableLiveData<S>() // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?
    private val actionHolder = MutableLiveData<A>()

    override fun getStateObservable(): MutableLiveData<S> {
        return stateHolder
    }

    protected fun setState(state: S) {
        // TODO merge states here
        // state.merge(stateHolder.value as S)
        stateHolder.value = state
    }

    protected fun getLastState(): S {
        return if (stateHolder.value == null) getDefaultState() else stateHolder.value!!
    }

    protected abstract fun getDefaultState(): S

    override fun getActionObservable(): MutableLiveData<A> {
        return actionHolder
    }

    protected fun setAction(action: A) {
        actionHolder.value = action
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
    }
}
