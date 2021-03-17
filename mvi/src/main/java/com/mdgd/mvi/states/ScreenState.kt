package com.mdgd.mvi.states

interface ScreenState<T> {
    fun visit(screen: T)
}
