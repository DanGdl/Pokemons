package com.mdgd.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, S : ScreenState<V, S>, A> : ViewModel(), FragmentContract.ViewModel<S, A> {
    private val stateHolder = MutableLiveData<S>()
    private val actionHolder = MutableLiveData<A>()

    override fun getStateObservable() = stateHolder

    override fun getEffectObservable() = actionHolder

    protected fun setState(state: S) {
        stateHolder.value?.let { state.merge(it) }
        stateHolder.value = state
    }

    protected fun getState() = stateHolder.value

    protected fun setAction(action: A) {
        actionHolder.value = action
    }

    override fun onStateChanged(event: Lifecycle.Event) {
    }
}
