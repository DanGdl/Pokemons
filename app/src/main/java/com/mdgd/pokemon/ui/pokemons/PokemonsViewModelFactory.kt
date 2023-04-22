package com.mdgd.pokemon.ui.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdgd.mvi.util.DispatchersHolderImpl
import com.mdgd.pokemon.models.AppModule

class PokemonsViewModelFactory(private val appComponent: AppModule) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == PokemonsViewModel::class.java) {
            PokemonsViewModel(
                appComponent.getPokemonsRepo(),
                appComponent.getFiltersFactory(),
                DispatchersHolderImpl()
            ) as T
        } else super.create(modelClass)
    }
}
