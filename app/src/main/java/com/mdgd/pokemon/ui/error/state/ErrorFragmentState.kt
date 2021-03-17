package com.mdgd.pokemon.ui.error.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.ui.error.ErrorContract

sealed class ErrorFragmentState : ScreenState<ErrorContract.View> {
    override fun visit(screen: ErrorContract.View) {}
}
