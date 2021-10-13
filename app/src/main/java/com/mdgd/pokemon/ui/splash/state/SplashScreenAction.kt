package com.mdgd.pokemon.ui.splash.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.splash.SplashContract

sealed class SplashScreenAction : AbstractAction<SplashContract.View>() {

    class ShowError(val e: Throwable) : SplashScreenAction() {

        override fun handle(screen: SplashContract.View) {
            screen.showError(e)
        }
    }

    object LaunchWorker : SplashScreenAction() {

        override fun handle(screen: SplashContract.View) {
            screen.launchWorker()
        }
    }

    object NextScreen : SplashScreenAction() {

        override fun handle(screen: SplashContract.View) {
            screen.proceedToNextScreen()
        }
    }
}
