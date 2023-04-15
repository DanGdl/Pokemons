package com.mdgd.mvi.states

interface ScreenState<V, S> {
    fun visit(screen: V)

    fun merge(prevState: S): S
}
