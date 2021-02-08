package com.mdgd.pokemon.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdgd.pokemon.models.AppModule

class PokemonDetailsViewModelFactory(private val appComponent: AppModule) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == PokemonDetailsViewModel::class.java) {
            PokemonDetailsViewModel(appComponent.getCache()) as T
        } else super.create(modelClass)
    }
}
