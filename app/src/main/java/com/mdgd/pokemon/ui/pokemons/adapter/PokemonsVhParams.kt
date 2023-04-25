package com.mdgd.pokemon.ui.pokemons.adapter

import android.view.ViewGroup
import kotlinx.coroutines.flow.MutableSharedFlow

class PokemonsVhParams(val parent: ViewGroup, val evensSubject: MutableSharedFlow<PokemonsEvent>)
