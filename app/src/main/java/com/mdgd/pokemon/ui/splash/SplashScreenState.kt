package com.mdgd.pokemon.ui.splash

import com.mdgd.mvi.ScreenState

sealed class SplashScreenState : ScreenState<SplashContract.View> {
    override fun visit(screen: SplashContract.View) {}
}
