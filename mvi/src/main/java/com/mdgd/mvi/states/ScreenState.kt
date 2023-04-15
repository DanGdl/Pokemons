package com.mdgd.mvi.states

interface ScreenState<V> {
    fun visit(screen: V)
}
