package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenEffect
import com.mdgd.pokemon.ui.pokemons.state.PokemonsScreenState

class PokemonsContract {
    interface ViewModel : FragmentContract.ViewModel<PokemonsScreenState, PokemonsScreenEffect> {
        fun reload()
        fun sort(filter: String)
        fun onItemClicked(pokemon: PokemonFullDataSchema)
        fun onScroll(firstVisibleIndex: Int, lastVisibleIndex: Int)
        fun firstVisible(): Int
    }

    interface View : FragmentContract.View {
        fun setProgressVisibility(isProgressVisible: Boolean)
        fun setItems(list: List<PokemonFullDataSchema>)
        fun showError(error: Throwable?)
        fun proceedToNextScreen(pokemonId: Long?)
        fun updateFilterButtons(activateFilter: Boolean, filter: String)
        fun scrollToStart()
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonScreen(pokemonId: Long?)
    }
}
