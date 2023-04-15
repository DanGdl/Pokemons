package com.mdgd.mvi.states

abstract class AbstractState<T, S> : ScreenState<T> {

    override fun visit(screen: T) {

    }

    open fun merge(prevState: S): S = this as S
}
