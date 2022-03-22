package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract

sealed class PokemonDetailsScreenEffect : AbstractEffect<PokemonDetailsContract.View>() {

    class EffectBack : PokemonDetailsScreenEffect() {
        override fun handle(screen: PokemonDetailsContract.View) {
            screen.goBack()
        }
    }
}
