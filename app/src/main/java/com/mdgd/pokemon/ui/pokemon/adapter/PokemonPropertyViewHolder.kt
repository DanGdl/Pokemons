package com.mdgd.pokemon.ui.pokemon.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mdgd.pokemon.ui.pokemon.infra.PokemonProperty

abstract class PokemonPropertyViewHolder<T : PokemonProperty>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setupSubscriptions() {}

    fun clearSubscriptions() {}

    abstract fun bind(property: T, position: Int)
}
