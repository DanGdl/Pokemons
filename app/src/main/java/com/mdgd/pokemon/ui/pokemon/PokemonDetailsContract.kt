package com.mdgd.pokemon.ui.pokemon

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenEffect
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState

class PokemonDetailsContract {
    interface ViewModel :
        FragmentContract.ViewModel<PokemonDetailsScreenState, PokemonDetailsScreenEffect> {
        fun setPokemonId(pokemonId: Long)
        fun onBackPressed()
    }

    interface View : FragmentContract.View {
        fun setItems(items: List<PokemonProperty>)
        fun goBack()
    }

    interface Host : FragmentContract.Host {
        fun onBackPressed()
    }
}
