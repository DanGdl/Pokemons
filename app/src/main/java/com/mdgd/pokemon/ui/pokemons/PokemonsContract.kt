package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.adapter.Pokemon

class PokemonsContract {
    interface ViewModel : FragmentContract.ViewModel<View> {
        fun reload()
        fun loadPage(page: Int)
        fun sort(filter: String)
        fun onItemClicked(pokemon: PokemonFullDataSchema)
    }

    interface View : FragmentContract.View {
        fun setProgressVisibility(isVisible: Boolean)
        fun setItems(list: List<Pokemon>)
        fun showError(error: Throwable?)
        fun proceedToNextScreen(pokemonId: Long?)
        fun updateFilterButtons(activateFilter: Boolean, filter: String)
        fun scrollToStart()
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonScreen(pokemonId: Long?)
        fun showError(error: Throwable?)
    }
}
