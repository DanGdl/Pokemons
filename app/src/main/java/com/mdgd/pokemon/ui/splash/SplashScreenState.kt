package com.mdgd.pokemon.ui.splash

import com.mdgd.mvi.ScreenState

sealed class SplashScreenState : ScreenState<SplashContract.View> {

    class ShowError(val e: Throwable) : SplashScreenState() {

        override fun visit(screen: SplashContract.View) {
            screen.showError(e)
        }
    }

    object None : SplashScreenState()

    object LaunchWorker : SplashScreenState() {

        override fun visit(screen: SplashContract.View) {
            screen.launchWorker()
        }
    }

    object NextScreen : SplashScreenState() {

        override fun visit(screen: SplashContract.View) {
            screen.proceedToNextScreen()
        }
    }

    override fun visit(screen: SplashContract.View) {}
}
