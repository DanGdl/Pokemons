package com.mdgd.pokemon.ui.pokemons.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.mdgd.pokemon.R
import com.mdgd.pokemon.adapter.AbstractListAdapter
import com.mdgd.pokemon.adapter.AbstractVH
import com.mdgd.pokemon.adapter.ViewHolderFactory
import com.mdgd.pokemon.ui.pokemon.dto.PokemonProperty

class PokemonsAdapter : AbstractListAdapter<Pokemon, PokemonsVhParams, PokemonsEvent>(object :
    DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}) {
    override fun createViewHolderFactories(): Map<Int, ViewHolderFactory<PokemonsVhParams>> =
        hashMapOf(
            PokemonProperty.EMPTY_VIEW to object : ViewHolderFactory<PokemonsVhParams> {
                override fun createViewHolder(params: PokemonsVhParams): AbstractVH<*> {
                    return EmptyViewHolder(inflate(R.layout.item_empty, params.parent))
                }
            },
            0 to object : ViewHolderFactory<PokemonsVhParams> {
                override fun createViewHolder(params: PokemonsVhParams): AbstractVH<*> {
                    return PokemonViewHolder(
                        inflate(R.layout.item_pokemon, params.parent),
                        params.evensSubject
                    )
                }
            }
        )

    override fun getViewHolderParams(parent: ViewGroup, viewType: Int) =
        PokemonsVhParams(parent, evensSubject)
}