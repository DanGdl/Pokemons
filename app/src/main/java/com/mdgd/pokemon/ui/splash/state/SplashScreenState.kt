package com.mdgd.pokemon.ui.splash.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.ui.splash.SplashContract

sealed class SplashScreenState : ScreenState<SplashContract.View> {

    object None : SplashScreenState()

    override fun visit(screen: SplashContract.View) {}
}
