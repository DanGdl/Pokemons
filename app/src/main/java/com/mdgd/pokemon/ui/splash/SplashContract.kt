package com.mdgd.pokemon.ui.splash

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.ui.splash.state.SplashScreenAction
import com.mdgd.pokemon.ui.splash.state.SplashScreenState

class SplashContract {
    companion object {
        const val SPLASH_DELAY = 1000L
    }


    interface ViewModel : FragmentContract.ViewModel<SplashScreenState, SplashScreenAction>

    interface View : FragmentContract.View {
        fun proceedToNextScreen()
        fun launchWorker()
        fun showError(error: Throwable?)
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonsScreen()
    }
}
