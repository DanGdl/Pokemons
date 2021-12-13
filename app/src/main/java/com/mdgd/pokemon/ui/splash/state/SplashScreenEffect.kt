package com.mdgd.pokemon.ui.splash.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.splash.SplashContract

sealed class SplashScreenEffect : AbstractEffect<SplashContract.View>() {

    class ShowError(val e: Throwable) : SplashScreenEffect() {

        override fun handle(screen: SplashContract.View) {
            screen.showError(e)
        }
    }


    object LaunchWorker : SplashScreenEffect() {

        override fun handle(screen: SplashContract.View) {
            screen.launchWorker()
        }
    }

    object NextScreen : SplashScreenEffect() {

        override fun handle(screen: SplashContract.View) {
            screen.proceedToNextScreen()
        }
    }
}
