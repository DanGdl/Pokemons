package com.mdgd.pokemon.ui.pokemons.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.pokemons.PokemonsContract

sealed class PokemonsScreenEffect : AbstractEffect<PokemonsContract.View>() {

    class Error(val error: Throwable?) : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.showError(error)
        }
    }

    class ShowDetails(val id: Long?) : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.hideProgress()
            screen.proceedToNextScreen(id)
        }
    }

    class ScrollToStart : PokemonsScreenEffect() {

        override fun handle(screen: PokemonsContract.View) {
            screen.scrollToStart()
        }
    }
}
