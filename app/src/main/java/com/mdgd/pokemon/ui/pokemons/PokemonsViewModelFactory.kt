package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdgd.pokemon.models.AppModule

class PokemonsViewModelFactory(private val appComponent: AppModule, private val router: PokemonsContract.Router) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == PokemonsViewModel::class.java) {
            PokemonsViewModel(router, appComponent.getPokemonsRepo()) as T
        } else super.create(modelClass)
    }
}
