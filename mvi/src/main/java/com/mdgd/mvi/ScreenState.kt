package com.mdgd.mvi

abstract class ScreenState<T : FragmentContract.View> {
    abstract fun visit(screen: T)
}