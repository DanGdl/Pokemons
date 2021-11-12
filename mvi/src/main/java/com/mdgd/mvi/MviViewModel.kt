package com.mdgd.mvi

import androidx.lifecycle.*
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, S : ScreenState<V, S>, A> : ViewModel(), FragmentContract.ViewModel<S, A> {
    private val stateHolder = MutableLiveData<S>() // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?
    private val actionHolder = MutableLiveData<A>()

    override fun getStateObservable(): MutableLiveData<S> {
        return stateHolder
    }

    protected fun setState(state: S) {
        if (stateHolder.value != null) {
            state.merge(stateHolder.value as S)
        }
        stateHolder.value = state
    }

    protected fun getState(): S? {
        return stateHolder.value
    }

    override fun getActionObservable(): MutableLiveData<A> {
        return actionHolder
    }

    protected fun setAction(action: A) {
        actionHolder.value = action
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
    }
}
