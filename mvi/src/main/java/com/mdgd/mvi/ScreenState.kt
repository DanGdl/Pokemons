package com.mdgd.mvi

abstract class ScreenState<T> {
    abstract fun visit(screen: T)
}
