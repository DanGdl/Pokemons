package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.ScreenState
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

sealed class PokemonDetailsScreenState : ScreenState<PokemonDetailsContract.View> {

    class SetData(val items: List<PokemonProperty>) : PokemonDetailsScreenState() {

        override fun visit(screen: PokemonDetailsContract.View) {
            screen.setItems(items)
        }
    }
}
