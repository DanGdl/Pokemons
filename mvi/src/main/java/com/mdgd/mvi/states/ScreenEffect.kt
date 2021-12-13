package com.mdgd.mvi.states

interface ScreenEffect<T> {
    fun visit(screen: T)
}
