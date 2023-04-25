package com.mdgd.pokemon.ui.pokemon.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty

class PokemonPropertiesDiffCallback : DiffUtil.ItemCallback<PokemonProperty>() {

    override fun areItemsTheSame(
        oldItem: PokemonProperty, newItem: PokemonProperty
    ): Boolean = oldItem === newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: PokemonProperty, newItem: PokemonProperty
    ): Boolean = oldItem == newItem
}
