package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenAction() : AbstractAction<PokemonsContract.View>() {

    class Loading() : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.showProgress()
        }
    }

    class HideProgress() : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.hideProgress()
        }
    }

    class Error(val error: Throwable?) : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.showError(error)
        }
    }

    class ShowDetails(val id: Long?) : PokemonsScreenAction() {

        override fun handle(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.proceedToNextScreen(id)
        }
    }
}
