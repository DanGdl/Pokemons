package com.mdgd.mvi.states

abstract class AbstractState<V, S> : ScreenState<V> {

    override fun visit(screen: V) {

    }

    open fun merge(prevState: S): S = this as S
}
