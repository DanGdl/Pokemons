package com.mdgd.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.mvi.states.AbstractState
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, STATE : AbstractState<V, STATE>, EFFECT : AbstractEffect<V>> :
    ViewModel(),
    FragmentContract.ViewModel<V> {
    private val stateHolder = MutableLiveData<ScreenState<V>>()
    private val effectHolder = MutableLiveData<ScreenState<V>>()

    override fun getStateObservable() = stateHolder

    override fun getEffectObservable() = effectHolder

    protected fun setState(state: STATE) {
        stateHolder.value = stateHolder.value?.let { state.merge(it as STATE) } ?: state
    }

    protected fun getState() = stateHolder.value as STATE?

    protected fun getEffect() = stateHolder.value as EFFECT?

    protected fun setEffect(action: EFFECT) {
        effectHolder.value = action
    }

    @CallSuper
    override fun onStateChanged(event: Lifecycle.Event) {
    }
}
