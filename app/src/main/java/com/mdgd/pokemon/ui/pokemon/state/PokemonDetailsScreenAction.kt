package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract

sealed class PokemonDetailsScreenAction : AbstractAction<PokemonDetailsContract.View>() {

    class ActionBack : PokemonDetailsScreenAction() {
        override fun handle(screen: PokemonDetailsContract.View) {
            screen.goBack()
        }
    }
}
