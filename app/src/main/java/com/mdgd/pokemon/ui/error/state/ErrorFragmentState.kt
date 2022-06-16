package com.mdgd.pokemon.ui.error.state

import com.mdgd.mvi.states.AbstractState
import com.mdgd.pokemon.ui.error.ErrorContract

sealed class ErrorFragmentState : AbstractState<ErrorContract.View, ErrorFragmentState>()
