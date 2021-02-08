package com.mdgd.pokemon.ui.pokemon.infra

import com.mdgd.mvi.ScreenState
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract

class PokemonDetailsScreenState(private val action: Int, private val items: List<PokemonProperty>) : ScreenState<PokemonDetailsContract.View>() {

    override fun visit(screen: PokemonDetailsContract.View) {
        if (action == ACTION_SET) {
            screen.setItems(items)
        }
    }

    companion object {
        private const val ACTION_SET = 1

        fun createSetDataState(properties: List<PokemonProperty>): PokemonDetailsScreenState {
            return PokemonDetailsScreenState(ACTION_SET, properties)
        }
    }
}
