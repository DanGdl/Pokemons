package com.mdgd.mvi

interface ScreenState<T> {
    fun visit(screen: T)
}
