package com.mdgd.mvi

import androidx.annotation.CallSuper
import androidx.lifecycle.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class MviViewModel<T> : ViewModel(), FragmentContract.ViewModel<T> {
    private val onStopDisposables = CompositeDisposable()
    private val onDestroyDisposables = CompositeDisposable()
    private val stateHolder = MutableLiveData<T>()

    override fun getStateObservable(): MutableLiveData<T> {
        return stateHolder
    }

    @CallSuper
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected open fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            onStopDisposables.clear()
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroyDisposables.clear()
        }
    }

    protected fun observeTillStop(vararg subscriptions: Disposable?) {
        onStopDisposables.addAll(*subscriptions)
    }

    protected fun observeTillDestroy(vararg subscriptions: Disposable?) {
        onDestroyDisposables.addAll(*subscriptions)
    }

    protected fun setState(state: T) {
        stateHolder.value = state
    }

    protected fun postState(state: T) {
        stateHolder.postValue(state)
    }

    protected fun hasOnDestroyDisposables(): Boolean {
        return onDestroyDisposables.size() != 0
    }

    protected fun hasOStopDisposables(): Boolean {
        return onStopDisposables.size() != 0
    }
}
