package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenAction() : AbstractAction<PokemonsContract.View>() {

    class Error(val error: Throwable?) : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.setProgressVisibility(false)
            screen.showError(error)
        }
    }

    class ShowDetails(val id: Long?) : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.setProgressVisibility(false)
            screen.proceedToNextScreen(id)
        }
    }
}
