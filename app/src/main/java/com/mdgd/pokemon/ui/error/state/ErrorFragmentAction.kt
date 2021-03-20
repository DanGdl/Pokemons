package com.mdgd.pokemon.ui.error.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.error.ErrorContract

sealed class ErrorFragmentAction : AbstractAction<ErrorContract.View>() {
    override fun handle(screen: ErrorContract.View) {}
}
