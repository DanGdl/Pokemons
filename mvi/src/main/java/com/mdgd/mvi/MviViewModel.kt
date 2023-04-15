package com.mdgd.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.mvi.states.ScreenState

abstract class MviViewModel<V, STATE> : ViewModel(), FragmentContract.ViewModel<V> {
    private val stateHolder = MutableLiveData<ScreenState<V>>()
    private val effectHolder = MutableLiveData<ScreenState<V>>()

    override fun getStateObservable() = stateHolder

    override fun getEffectObservable() = effectHolder

    protected fun setState(state: ScreenState<V>) {
        stateHolder.value = state
    }

    protected fun getState() = stateHolder.value as STATE?

    protected fun setEffect(action: ScreenState<V>) {
        effectHolder.value = action
    }

    @CallSuper
    override fun onStateChanged(event: Lifecycle.Event) {
    }
}
