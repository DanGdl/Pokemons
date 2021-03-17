package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.AbstractAction
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract

sealed class PokemonDetailsScreenAction : AbstractAction<PokemonDetailsContract.View>()
