package com.mdgd.pokemon.ui.pokemon.adapter

import android.view.ViewGroup
import com.mdgd.pokemon.ui.adapter.RecyclerVH
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

interface ViewHolderFactory {
    fun create(parent: ViewGroup?): RecyclerVH<out PokemonProperty>
}
