package com.mdgd.pokemon.ui.splash

import com.mdgd.mvi.FragmentContract

class SplashContract {
    interface ViewModel : FragmentContract.ViewModel<SplashScreenState>

    interface View : FragmentContract.View {
        fun proceedToNextScreen()
        fun launchWorker()
        fun showError(error: Throwable?)
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonsScreen()
        fun showError(error: Throwable?)
    }
}
