package com.mdgd.pokemon.ui.pokemon

import com.mdgd.mvi.fragments.FragmentContract
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenEffect
import com.mdgd.pokemon.ui.pokemon.state.PokemonDetailsScreenState

class PokemonDetailsContract {

    interface ViewModel :
        FragmentContract.ViewModel<PokemonDetailsScreenState, PokemonDetailsScreenEffect> {
        fun setPokemonId(pokemonId: Long)
    }

    interface View : FragmentContract.View {
        fun setItems(items: List<PokemonProperty>)
    }

    interface Host : FragmentContract.Host
}
