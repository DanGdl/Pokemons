package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdgd.pokemon.models.AppModule

class SplashViewModelFactory(private val appComponent: AppModule, private val router: SplashContract.Router) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == SplashViewModel::class.java) {
            SplashViewModel(router, appComponent.getCache()) as T
        } else super.create(modelClass)
    }
}
