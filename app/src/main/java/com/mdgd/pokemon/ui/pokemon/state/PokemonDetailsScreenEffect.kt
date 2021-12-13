package com.mdgd.pokemon.ui.pokemon.state

import com.mdgd.mvi.states.AbstractEffect
import com.mdgd.pokemon.ui.pokemon.PokemonDetailsContract

sealed class PokemonDetailsScreenEffect : AbstractEffect<PokemonDetailsContract.View>()
