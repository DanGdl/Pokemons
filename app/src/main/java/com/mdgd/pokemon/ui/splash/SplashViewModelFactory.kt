package com.mdgd.pokemon.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdgd.pokemon.models.AppModule

class SplashViewModelFactory(private val appComponent: AppModule) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == SplashViewModel::class.java) {
            SplashViewModel(appComponent.getCache()) as T
        } else super.create(modelClass)
    }
}
