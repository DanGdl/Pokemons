package com.mdgd.pokemon.ui.pokemon.adapter.holders

import android.view.ViewGroup
import com.mdgd.pokemon.ui.pokemon.adapter.PokemonPropertyViewHolder
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

interface ViewHolderFactory {
    fun create(parent: ViewGroup?): PokemonPropertyViewHolder<out PokemonProperty>
}