package com.mdgd.pokemon.ui.pokemon

import com.mdgd.mvi.FragmentContract
import com.mdgd.pokemon.ui.pokemon.infra.PokemonDetailsScreenState
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

class PokemonDetailsContract {
    interface ViewModel : FragmentContract.ViewModel<PokemonDetailsScreenState>

    interface View : FragmentContract.View {
        fun setItems(items: List<PokemonProperty>)
    }

    interface Host : FragmentContract.Host
}
