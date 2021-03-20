package com.mdgd.pokemon.ui.splash.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.ui.splash.SplashContract

sealed class SplashScreenState : ScreenState<SplashContract.View, SplashScreenState> {

    override fun visit(screen: SplashContract.View) {}

    override fun merge(prevState: SplashScreenState) {}
}
