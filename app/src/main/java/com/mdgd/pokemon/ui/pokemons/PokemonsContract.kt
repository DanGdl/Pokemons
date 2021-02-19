package com.mdgd.pokemon.ui.pokemons

import com.mdgd.mvi.FragmentContract
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema
import com.mdgd.pokemon.ui.pokemons.infra.FilterData

class PokemonsContract {
    interface ViewModel : FragmentContract.ViewModel<PokemonsScreenState> {
        fun reload()
        fun loadPage(page: Int)
        fun sort(filterData: FilterData)
        fun onItemClicked(pokemon: PokemonFullDataSchema)
    }

    interface View : FragmentContract.View {
        fun showProgress()
        fun hideProgress()
        fun setItems(list: List<PokemonFullDataSchema>)
        fun updateItems(list: List<PokemonFullDataSchema>)
        fun showError(error: Throwable?)
        fun proceedToNextScreen(pokemonId: Long?)
    }

    interface Host : FragmentContract.Host {
        fun proceedToPokemonScreen(pokemonId: Long?)
        fun showError(error: Throwable?)
    }
}