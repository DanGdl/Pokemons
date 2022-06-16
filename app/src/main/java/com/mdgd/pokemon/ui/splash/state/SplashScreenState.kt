package com.mdgd.pokemon.ui.splash.state

import com.mdgd.mvi.states.AbstractState
import com.mdgd.pokemon.ui.splash.SplashContract

sealed class SplashScreenState : AbstractState<SplashContract.View, SplashScreenState>()
