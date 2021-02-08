package com.mdgd.pokemon.ui.splash

import com.mdgd.mvi.FragmentContract

class SplashContract {
    interface ViewModel : FragmentContract.ViewModel<SplashScreenState>

    interface View : FragmentContract.View

    interface Host : FragmentContract.Host {
        fun proceedToPokemonsScreen()
        fun showError(error: Throwable?)
    }

    interface Router {
        fun proceedToNextScreen()
        fun launchWorker()
        fun showError(error: Throwable?)
    }
}
