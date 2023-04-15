package com.mdgd.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenEffect
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, S : ScreenState<V, S>, E : ScreenEffect<V>> : ViewModel(),
    FragmentContract.ViewModel<V, S, E> {
    private val stateHolder = MutableLiveData<S>()
    private val effectHolder = MutableLiveData<E>()

    override fun getStateObservable() = stateHolder

    override fun getEffectObservable() = effectHolder

    protected fun setState(state: S) {
        stateHolder.value = stateHolder.value?.let {
            state.merge(it)
        } ?: state
    }

    protected fun getState() = stateHolder.value

    protected fun setEffect(action: E) {
        effectHolder.value = action
    }

    @CallSuper
    override fun onStateChanged(event: Lifecycle.Event) {
    }
}
