package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.AbstractState
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty

open class PokemonDetailsScreenState(
    val items: List<PokemonProperty>
) : AbstractState<PokemonDetailsContract.View, PokemonDetailsScreenState>() {

    override fun visit(screen: PokemonDetailsContract.View) {
        screen.setItems(items)
    }

    // PARTIAL STATES

    class SetData(items: List<PokemonProperty>) : PokemonDetailsScreenState(items)

    // EOF: PARTIAL STATES
}
