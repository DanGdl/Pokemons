package com.mdgd.pokemon.ui.pokemon

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

sealed class PokemonDetailsScreenState : ScreenState<PokemonDetailsContract.View> {

    class SetData(val items: List<PokemonProperty>) : PokemonDetailsScreenState() {

        override fun visit(screen: PokemonDetailsContract.View) {
            screen.setItems(items)
        }
    }
}
