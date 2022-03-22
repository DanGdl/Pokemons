package com.mdgd.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, S : ScreenState<V, S>, E> : ViewModel(),
    FragmentContract.ViewModel<S, E> {
    private val stateHolder =
        MutableLiveData<S>() // TODO: use StateFlow: val uiState: StateFlow<LatestNewsUiState> = _uiState ?
    private val effectHolder = MutableLiveData<E>()

    override fun getStateObservable(): LiveData<S> = stateHolder

    override fun getActionObservable(): LiveData<E> = effectHolder

    protected fun setState(state: S) {
        stateHolder.value?.let { state.merge(it) }
        stateHolder.value = state
    }

    protected fun getState() = stateHolder.value

    protected fun setAction(effect: E) {
        effectHolder.value = effect
    }

    override fun onStateChanged(event: Lifecycle.Event) {
    }
}
