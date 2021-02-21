package com.mdgd.pokemon.ui.error

import com.mdgd.mvi.ScreenState

sealed class ErrorFragmentState : ScreenState<ErrorContract.View> {
    override fun visit(screen: ErrorContract.View) {}
}
