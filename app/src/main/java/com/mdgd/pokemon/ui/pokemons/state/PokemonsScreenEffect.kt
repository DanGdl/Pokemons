package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenEffect : AbstractEffect<PokemonsContract.View>() {

    class Error(val error: Throwable?) : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.setProgressVisibility(false)
            screen.showError(error)
        }
    }

    class ShowDetails(val id: Long?) : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.setProgressVisibility(false)
            screen.proceedToNextScreen(id)
        }
    }

    class ScrollToStart : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.scrollToStart()
        }
    }
}
