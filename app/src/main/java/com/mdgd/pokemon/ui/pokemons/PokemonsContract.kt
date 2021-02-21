package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.FragmentContract
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema

class PokemonsContract {
    interface ViewModel : FragmentContract.ViewModel<PokemonsScreenState> {
        fun reload()
        fun loadPage(page: Int)
        fun sort(filter: String)
        fun onItemClicked(pokemon: PokemonFullDataSchema)
    }

    interface View : FragmentContract.View {
        fun showProgress()
        fun hideProgress()
        fun setItems(list: List<PokemonFullDataSchema>)
        fun updateItems(list: List<PokemonFullDataSchema>)
        fun showError(error: Throwable?)
        fun proceedToNextScreen(pokemonId: Long?)
        fun updateFilterButtons(activateFilter: Boolean, filter: String)
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonScreen(pokemonId: Long?)
        fun showError(error: Throwable?)
    }
}
