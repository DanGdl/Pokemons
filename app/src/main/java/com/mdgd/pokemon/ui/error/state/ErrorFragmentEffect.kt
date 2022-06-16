package com.mdgd.pokemon.ui.error.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.error.ErrorContract

sealed class ErrorFragmentEffect : AbstractEffect<ErrorContract.View>()
